package com.umutavci.imdb.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ActorMovie {
    @EmbeddedId
    ActorMovieKey id;
    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actorId")
    Actor actor;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movieId")
    Movie movie;

}
