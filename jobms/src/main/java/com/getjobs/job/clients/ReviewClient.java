package com.getjobs.job.clients;

import com.getjobs.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "REVIEW-SERVICE", url = "${review-service.url}")
public interface ReviewClient {

    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long id);
}