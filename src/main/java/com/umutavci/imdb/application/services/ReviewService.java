package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.in.DirectorInput;
import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.out.DirectorResponse;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;
import com.umutavci.imdb.domain.ports.repositories.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements BaseService<ReviewInput, ReviewResponse>{
    @Autowired
    private final IReviewRepository repository;

    public ReviewService(IReviewRepository repository) {
        this.repository = repository;
    }
    @Override
    public ReviewResponse getSingle(Long id){ return repository.getSingle(id); }
    @Override
    public List<ReviewResponse> getAll(){ return repository.getAll(); }
    @Override
    public ReviewResponse create(ReviewInput input){ return repository.create(input); }
    @Override
    public ReviewResponse update(Long id, ReviewInput input){ return repository.update(id, input); }
    @Override
    public boolean delete(Long id){ return repository.delete(id); }
}
