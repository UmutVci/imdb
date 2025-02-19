package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.DirectorInput;

import com.umutavci.imdb.domain.models.out.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorMapper INSTANCE = Mappers.getMapper(DirectorMapper.class);

    @Mapping(target = "id", ignore = true)
    Director toDirector(DirectorInput input);

    Director toDirectorResponse(Director Director);
}



