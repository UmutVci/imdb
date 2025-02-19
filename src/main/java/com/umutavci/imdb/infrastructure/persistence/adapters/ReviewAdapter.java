package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Review;
import com.umutavci.imdb.infrastructure.persistence.mapper.ReviewMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.MovieJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.ReviewJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewAdapter implements ICRUDAdapter<ReviewInput, ReviewResponse> {

    @Autowired
    private final ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private final ReviewMapper reviewMapper;

    private final MovieJpaRepository movieJpaRepository;

    private final UserJpaRepository userJpaRepository;

    public ReviewAdapter(ReviewJpaRepository reviewJpaRepository, ReviewMapper reviewMapper, MovieJpaRepository movieJpaRepository, UserJpaRepository userJpaRepository) {
        this.reviewJpaRepository = reviewJpaRepository;
        this.reviewMapper = reviewMapper;
        this.movieJpaRepository = movieJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public ReviewResponse getSingle(Long id) {
        Review review = reviewJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public List<ReviewResponse> getAll() {
        return reviewJpaRepository.findAll()
                .stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }

    @Override
    public ReviewResponse create(ReviewInput reviewInput) {
        Review review = reviewMapper.toReview(reviewInput);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        Review savedReview = reviewJpaRepository.save(review);
        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    public ReviewResponse update(Long id, ReviewInput reviewInput) {
        Review review = reviewJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setMovie(movieJpaRepository.findById(reviewInput.getMovieId()).orElseThrow());
        review.setUserid(userJpaRepository.findById(reviewInput.getUserId()).orElseThrow());
        review.setRating(reviewInput.getRating());
        review.setComment(reviewInput.getComment());
        review.setUpdatedAt(LocalDateTime.now());
        Review savedReview = reviewJpaRepository.save(review);
        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    public boolean delete(Long id) {
        Review review = reviewJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewJpaRepository.delete(review);
        return !reviewJpaRepository.findById(id).isPresent();
    }
}

