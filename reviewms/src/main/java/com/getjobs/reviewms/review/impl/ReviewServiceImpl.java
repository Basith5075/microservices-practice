package com.getjobs.reviewms.review.impl;


import com.getjobs.reviewms.review.Review;
import com.getjobs.reviewms.review.ReviewRepository;
import com.getjobs.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        if (companyId != null && review != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review != null){
            review.setCompanyId(updatedReview.getCompanyId());
            review.setDescription(updatedReview.getDescription());
            review.setTitle(updatedReview.getTitle());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            reviewRepository.deleteById(reviewId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Review> getAllReviewsByComp(Long companyId) {

        return reviewRepository.findByCompanyId(companyId);
    }
}
