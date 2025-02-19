package com.umutavci.imdb.domain.ports.repositories;


import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T entity);
    void deleteById(Long id);
    T update(T dto, Long id);
}
