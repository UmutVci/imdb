package com.umutavci.imdb.domain.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Base {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
