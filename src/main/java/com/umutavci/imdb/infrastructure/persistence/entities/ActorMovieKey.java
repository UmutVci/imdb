package com.umutavci.imdb.infrastructure.persistence.entities;


import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@Embeddable
public class ActorMovieKey implements Serializable {

    private Long actorId;
    private Long movieId;
}

