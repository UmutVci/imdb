package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data

public class Review extends Base {
    private Double rating;
    private String comment;
    private Long movieId;
    private Long userId;
}
