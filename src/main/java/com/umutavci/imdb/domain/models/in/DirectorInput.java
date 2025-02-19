package com.umutavci.imdb.domain.models.in;

import lombok.Data;

import java.time.LocalDate;

@Data

public class DirectorInput{
    private String name;
    private LocalDate birthDate;
}
