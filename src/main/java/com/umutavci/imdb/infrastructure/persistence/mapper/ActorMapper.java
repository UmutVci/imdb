package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.ActorInput;
import com.umutavci.imdb.domain.models.out.Actor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    @Mapping(target = "id", ignore = true)
    Actor toActor(ActorInput input);

    Actor toActorResponse(Actor actor);
}
