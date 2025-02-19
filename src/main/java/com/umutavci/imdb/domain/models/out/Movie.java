package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

import java.time.LocalDate;

@Data

public class Movie extends Base {
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private Double rating;
    private String description;
    private Long directorId;
}
