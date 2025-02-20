package com.umutavci.imdb.domain.ports.repositories;

import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.out.ReviewResponse;

public interface IReviewRepository extends IBaseRepository<ReviewInput, ReviewResponse>{
}
