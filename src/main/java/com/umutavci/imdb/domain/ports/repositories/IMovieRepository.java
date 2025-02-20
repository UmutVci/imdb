package com.umutavci.imdb.domain.ports.repositories;

import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;

import java.util.List;

public interface IMovieRepository extends IBaseRepository<MovieInput, MovieResponse>{
    List<MovieResponse> searchMovieByName(String name);
    List<MovieResponse> sortMovieByDescName();
    List<MovieResponse> sortMovieByBetterReviewPoint();
}
