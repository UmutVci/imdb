package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import com.umutavci.imdb.infrastructure.persistence.entities.User;
import com.umutavci.imdb.infrastructure.persistence.repositories.MovieJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.UserJpaRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapperHelper {

    @Autowired
    private final MovieJpaRepository movieJpaRepository;
    @Autowired
    private final UserJpaRepository userJpaRepository;

    public ReviewMapperHelper(MovieJpaRepository movieJpaRepository, UserJpaRepository userJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }
    @Named("mapMovie")
    public Movie mapMovie(Long movieId) {
        return movieJpaRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
    }
    @Named("mapUser")
    public User mapUser(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
