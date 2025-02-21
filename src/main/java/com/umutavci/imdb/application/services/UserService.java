package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.in.ReviewInput;
import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.domain.models.out.UserResponse;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;
import com.umutavci.imdb.domain.ports.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements BaseService<UserInput, UserResponse>{
    @Autowired
    private final IUserRepository repository;
    public UserService(IUserRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserResponse getSingle(Long id){ return repository.getSingle(id); }
    @Override
    public List<UserResponse> getAll(){ return repository.getAll(); }
    @Override
    public UserResponse create(UserInput input){ return repository.create(input); }
    @Override
    public UserResponse update(Long id, UserInput input){ return repository.update(id, input); }
    @Override
    public boolean delete(Long id){ return repository.delete(id); }
}
