package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data

public class ReviewResponse extends Base {
    private Double rating;
    private String comment;
    private Long movieId;
    private Long userId;
}
