package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

import java.time.LocalDate;

@Data

public class DirectorInput extends Base {
    private String name;
    private LocalDate birthDate;
}
