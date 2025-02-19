package com.umutavci.imdb.domain.models.out;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class ResponseBase {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
