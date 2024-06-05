package com.getjobs.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    boolean addReview(Long companyId, Review review);
    Review getReview(Long reviewId);
    boolean updateReview(Long reviewId, Review review);
    boolean deleteReview(Long reviewId);
    List<Review> getAllReviewsByComp(Long companyId);
}
