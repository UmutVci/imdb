package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    @Autowired
    private MovieMapperHelper movieMapperHelper;
    public Movie toMovie(MovieInput input) {
        if ( input == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setDirector( movieMapperHelper.mapDirector( input.getDirectorId() ) );
        movie.setTitle( input.getTitle() );
        movie.setReleaseDate( input.getReleaseDate() );
        movie.setGenre( input.getGenre() );
        movie.setDescription( input.getDescription() );

        return movie;
    }

    public MovieResponse toMovieResponse(Movie Movie) {
        if ( Movie == null ) {
            return null;
        }

        MovieResponse movieResponse = new MovieResponse();

        movieResponse.setId( Movie.getId() );
        movieResponse.setCreatedAt( Movie.getCreatedAt() );
        movieResponse.setUpdatedAt( Movie.getUpdatedAt() );
        movieResponse.setTitle( Movie.getTitle() );
        movieResponse.setReleaseDate( Movie.getReleaseDate() );
        movieResponse.setGenre( Movie.getGenre() );
        movieResponse.setDescription( Movie.getDescription() );

        movieResponse.setDirectorId( Movie.getDirector().getId() );

        return movieResponse;
    }
}


