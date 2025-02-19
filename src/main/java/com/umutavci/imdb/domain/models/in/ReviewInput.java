package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data

public class ReviewInput extends Base {
    private Double rating;
    private String comment;
    private Long movieId;
    private Long userId;
}
