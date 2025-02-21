package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = MovieMapperHelper.class)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "director", source = "directorId", qualifiedByName = "mapDirector")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Movie toMovie(MovieInput input);
    @Mapping(target = "directorId", expression="java(Movie.getDirector().getId())")
    MovieResponse toMovieResponse(Movie Movie);
}


