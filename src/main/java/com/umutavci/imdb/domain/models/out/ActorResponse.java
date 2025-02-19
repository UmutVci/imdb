package com.umutavci.imdb.domain.models.out;

import lombok.Data;

import java.time.LocalDate;

@Data

public class ActorResponse extends ResponseBase {
    private String name;
    private LocalDate birthDate;
}
