package com.umutavci.imdb.domain.resolvers.mutations;

import com.umutavci.imdb.application.services.ReviewService;
import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewMutationResolver {
    @Autowired
    private final ReviewService reviewService;

    public ReviewMutationResolver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ReviewResponse createReview(@Argument ReviewInput input){
        return reviewService.create(input);
    }
    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public boolean deleteReview(@Argument Long id){
        return reviewService.delete(id);
    }
    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ReviewResponse updateReview(@Argument Long id, @Argument ReviewInput input){
        return reviewService.update(id, input);
    }
}
