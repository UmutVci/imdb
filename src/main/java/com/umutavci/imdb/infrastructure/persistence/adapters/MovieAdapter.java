package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.ports.repositories.IMovieRepository;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import com.umutavci.imdb.infrastructure.persistence.entities.Review;
import com.umutavci.imdb.infrastructure.persistence.mapper.MovieMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.DirectorJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.MovieJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.ReviewJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieAdapter implements IMovieRepository {

    @Autowired
    private final MovieJpaRepository movieJpaRepository;

    private final ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private final MovieMapper movieMapper;

    private final DirectorJpaRepository directorJpaRepository;

    public MovieAdapter(MovieJpaRepository movieJpaRepository, ReviewJpaRepository reviewJpaRepository, MovieMapper movieMapper, DirectorJpaRepository directorJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
        this.reviewJpaRepository = reviewJpaRepository;
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

    @Override
    public List<MovieResponse> searchMovieByName(String name) {
        return movieJpaRepository.findAll()
                .stream()
                .filter(m -> m.getTitle().toLowerCase().contains(name.toLowerCase()))
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> sortMovieByDescName() {
        return movieJpaRepository.findAll()
                .stream()
                .sorted((m1, m2) -> m1.getTitle().compareTo(m2.getTitle()))
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> sortMovieByBetterReviewPoint() {
        return movieJpaRepository.findAll()
                .stream()
                .map(m1 -> {
                    double averageRating = reviewJpaRepository.findAll()
                            .stream()
                            .filter(x -> x.getMovie().getId() == m1.getId())
                            .mapToDouble(Review::getRating)
                            .average()
                            .orElse(0.0);

                    return new Object[] { m1, averageRating };
                })
                .sorted((entry1, entry2) -> Double.compare((double) entry2[1], (double) entry1[1])) // Yüksekten düşüğe sıralama
                .map(entry -> (Movie) entry[0])
                .map(movieMapper::toMovieResponse)
                .toList();
    }
}
