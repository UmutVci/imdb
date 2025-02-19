package com.umutavci.imdb.domain.models.in;

import lombok.Data;

@Data

public class ReviewInput{
    private Double rating;
    private String comment;
    private Long movieId;
    private Long userId;
}
