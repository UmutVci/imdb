package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

import java.time.LocalDate;

@Data

public class ActorResponse extends Base {
    private String name;
    private LocalDate birthDate;
}
