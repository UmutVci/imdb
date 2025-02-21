package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieInput extends Base {
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private String description;
    private Long directorId;
}
