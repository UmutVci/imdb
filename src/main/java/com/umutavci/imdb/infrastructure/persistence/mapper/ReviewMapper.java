package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ReviewMapper {
    @Autowired
    private ReviewMapperHelper reviewMapperHelper;

    public Review toReview(ReviewInput input) {
        if ( input == null ) {
            return null;
        }

        Review review = new Review();

        review.setMovie( reviewMapperHelper.mapMovie( input.getMovieId() ) );
        review.setUserid( reviewMapperHelper.mapUser( input.getUserId() ) );
        review.setRating( input.getRating() );
        review.setComment( input.getComment() );

        return review;
    }

    public ReviewResponse toReviewResponse(Review Review) {
        if ( Review == null ) {
            return null;
        }

        ReviewResponse reviewResponse = new ReviewResponse();

        reviewResponse.setId( Review.getId() );
        reviewResponse.setCreatedAt( Review.getCreatedAt() );
        reviewResponse.setUpdatedAt( Review.getUpdatedAt() );
        reviewResponse.setRating( Review.getRating() );
        reviewResponse.setComment( Review.getComment() );

        reviewResponse.setMovieId( Review.getMovie().getId() );
        reviewResponse.setUserId( Review.getUserid().getId() );

        return reviewResponse;
    }
}



