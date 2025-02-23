package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.ActorInput;

import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper {
    public Actor toActor(ActorInput input) {
        if ( input == null ) {
            return null;
        }

        Actor actor = new Actor();

        actor.setName( input.getName() );
        actor.setBirthDate( input.getBirthDate() );

        return actor;
    }

    public ActorResponse toActorResponse(Actor actor) {
        if ( actor == null ) {
            return null;
        }

        ActorResponse actorResponse = new ActorResponse();

        actorResponse.setId( actor.getId() );
        actorResponse.setCreatedAt( actor.getCreatedAt() );
        actorResponse.setUpdatedAt( actor.getUpdatedAt() );
        actorResponse.setName( actor.getName() );
        actorResponse.setBirthDate( actor.getBirthDate() );

        return actorResponse;
    }
}
