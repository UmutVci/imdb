package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.infrastructure.persistence.entities.Director;
import com.umutavci.imdb.infrastructure.persistence.repositories.DirectorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieMapperHelper {
    @Autowired
    private final DirectorJpaRepository directorJpaRepository;

    public MovieMapperHelper(DirectorJpaRepository directorJpaRepository) {
        this.directorJpaRepository = directorJpaRepository;
    }
    public Director mapDirector(Long directorId) {
        return directorJpaRepository.findById(directorId).orElseThrow(() -> new RuntimeException("Director not found"));
    }
}
