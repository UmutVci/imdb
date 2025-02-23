package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.DirectorInput;

import com.umutavci.imdb.domain.models.out.DirectorResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Director;
import org.springframework.stereotype.Component;

@Component

public class DirectorMapper {
    public Director toDirector(DirectorInput input) {
        if ( input == null ) {
            return null;
        }

        Director director = new Director();

        director.setName( input.getName() );
        director.setBirthDate( input.getBirthDate() );

        return director;
    }

    public DirectorResponse toDirectorResponse(Director Director) {
        if ( Director == null ) {
            return null;
        }

        DirectorResponse directorResponse = new DirectorResponse();

        directorResponse.setBirthDate( Director.getBirthDate() );
        directorResponse.setId( Director.getId() );
        directorResponse.setCreatedAt( Director.getCreatedAt() );
        directorResponse.setUpdatedAt( Director.getUpdatedAt() );
        directorResponse.setName( Director.getName() );

        return directorResponse;
    }
}



