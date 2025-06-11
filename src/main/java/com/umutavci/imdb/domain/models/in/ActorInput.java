package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ActorInput extends Base {
    private String name;
    private LocalDate birthDate;
}
