package com.umutavci.imdb.domain.resolvers.queries;

import com.umutavci.imdb.application.services.ReviewService;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReviewQueryResolver {
    @Autowired
    private final ReviewService reviewService;

    public ReviewQueryResolver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @QueryMapping
    public ReviewResponse getReview(@Argument Long id){
        return reviewService.getSingle(id);
    }
    @QueryMapping
    public List<ReviewResponse> getAllReviews(){
        return reviewService.getAll();
    }
}
