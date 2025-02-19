package com.umutavci.imdb.domain.models.out;

import lombok.Data;

import java.time.LocalDate;

@Data

public class MovieResponse extends ResponseBase {
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private Double rating;
    private String description;
    private Long directorId;
}
