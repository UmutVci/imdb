package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import com.umutavci.imdb.infrastructure.persistence.mapper.MovieMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.DirectorJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.MovieJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieAdapter implements ICRUDAdapter<MovieInput, MovieResponse> {

    @Autowired
    private final MovieJpaRepository movieJpaRepository;

    @Autowired
    private final MovieMapper movieMapper;

    private final DirectorJpaRepository directorJpaRepository;

    public MovieAdapter(MovieJpaRepository movieJpaRepository, MovieMapper movieMapper, DirectorJpaRepository directorJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
        this.movieMapper = movieMapper;
        this.directorJpaRepository = directorJpaRepository;
    }

    @Override
    public MovieResponse getSingle(Long id) {
        Movie movie = movieJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAll() {
        return movieJpaRepository.findAll()
                .stream()
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public MovieResponse create(MovieInput movieInput) {
        Movie movie = movieMapper.toMovie(movieInput);
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        Movie savedMovie = movieJpaRepository.save(movie);
        return movieMapper.toMovieResponse(savedMovie);
    }

    @Override
    public MovieResponse update(Long id, MovieInput movieInput) {
        Movie movie = movieJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieInput.getTitle());
        movie.setReleaseDate(movieInput.getReleaseDate());
        movie.setDescription(movieInput.getDescription());
        movie.setDirector(directorJpaRepository.findById(movieInput.getDirectorId()).orElseThrow());
        movie.setGenre(movieInput.getGenre());
        movie.setRating(movieInput.getRating());
        movie.setUpdatedAt(LocalDateTime.now());
        Movie savedMovie = movieJpaRepository.save(movie);
        return movieMapper.toMovieResponse(savedMovie);
    }

    @Override
    public boolean delete(Long id) {
        Movie movie = movieJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieJpaRepository.delete(movie);
        return !movieJpaRepository.findById(id).isPresent();
    }
}
