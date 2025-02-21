package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ReviewMapperHelper.class)
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", source = "movieId", qualifiedByName = "mapMovie")
    @Mapping(target = "userid", source = "userId", qualifiedByName = "mapUser")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review toReview(ReviewInput input);

    @Mapping(target = "movieId", expression="java(Review.getMovie().getId())")
    @Mapping(target = "userId", expression="java(Review.getUserid().getId())")
    ReviewResponse toReviewResponse(Review Review);
}



