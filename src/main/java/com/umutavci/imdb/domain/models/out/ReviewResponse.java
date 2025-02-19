package com.umutavci.imdb.domain.models.out;

import lombok.Data;

@Data

public class ReviewResponse extends ResponseBase {
    private Double rating;
    private String comment;
    private Long movieId;
    private Long userId;
}
