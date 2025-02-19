package com.umutavci.imdb.domain.models.in;

import lombok.Data;

import java.time.LocalDate;

@Data

public class MovieInput {
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private Double rating;
    private String description;
    private Long directorId;
}
