package com.umutavci.imdb.infrastructure.persistence.entities;


import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class ActorMovieKey implements Serializable {

    private Long actorId;
    private Long movieId;
}
