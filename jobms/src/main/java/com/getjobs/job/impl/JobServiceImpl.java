package com.getjobs.job.impl;

import com.getjobs.job.Job;
import com.getjobs.job.JobRepository;
import com.getjobs.job.JobService;
import com.getjobs.job.clients.CompanyClient;
import com.getjobs.job.clients.ReviewClient;
import com.getjobs.job.dto.JobWithCompanyDTO;
import com.getjobs.job.external.Company;
import com.getjobs.job.external.Review;
import com.getjobs.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    // private List<Job> jobs = new ArrayList<>();

    JobRepository jobRepository;

    CompanyClient companyClient;

    ReviewClient reviewClient;

    int attempts = 0;
    @Autowired
    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name= "companyBreaker", fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
       @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();
//        System.out.println("attempts = " + attempts);
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Fall back method getting created

    public List<String> companyBreakerFallback(Exception e) {

        List<String> list = new ArrayList<>();;
        list.add("Something went wrong, please try again later");
        return list;
    }

    public JobWithCompanyDTO convertToDto(Job job){
    // This method takes a Job object, calls the Company API to get the company details.
    // Adds the company and job object to JobWithCompanyDTO and returns back.

//        Rest Template have a lot of boiler plate code, hence I am using OpenFeign to call the companies microservice

//            RestTemplate restTemplate = new RestTemplate();
//            Company company = restTemplate.getForObject(
//                    "http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(),
//                    Company.class);

        Company company = companyClient.getCompany(job.getCompanyId());

        //        Rest Template have a lot of boiler plate code, hence I am using OpenFeign to call the Reviews microservice
        // We are expecting a list of reviews here, hence we need to use exchange of RestTemplate
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + company.getId(),
//                HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
//                });
//        List<Review> reviews = reviewResponse.getBody();

        List<Review> reviews = reviewClient.getReviews(company.getId());


        JobWithCompanyDTO jobWithCompanyDTO =   JobMapper.jobWithCompanyMapper(job,company,reviews);

            return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {

        Job job = jobRepository.findById(id).orElse(null);

        JobWithCompanyDTO jobWithCompanyDTO = this.convertToDto(job);

        return jobWithCompanyDTO;

    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}