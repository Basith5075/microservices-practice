package com.getjobs.mapper;

import com.getjobs.job.Job;
import com.getjobs.job.dto.JobWithCompanyDTO;
import com.getjobs.job.external.Company;
import com.getjobs.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobWithCompanyDTO jobWithCompanyMapper(Job job, Company company, List<Review> reviews) {

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReview(reviews);

        return jobWithCompanyDTO;
    }
}
