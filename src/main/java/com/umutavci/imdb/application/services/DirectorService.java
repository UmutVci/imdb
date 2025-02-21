package com.umutavci.imdb.application.services;

import com.umutavci.imdb.domain.models.in.ActorInput;
import com.umutavci.imdb.domain.models.in.DirectorInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.models.out.DirectorResponse;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;
import com.umutavci.imdb.domain.ports.repositories.IDirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService implements BaseService<DirectorInput, DirectorResponse>{
    @Autowired
    private final IDirectorRepository repository;
    public DirectorService(IDirectorRepository repository) {
        this.repository = repository;
    }

    @Override
    public DirectorResponse getSingle(Long id){ return repository.getSingle(id); }
    @Override
    public List<DirectorResponse> getAll(){ return repository.getAll(); }
    @Override
    public DirectorResponse create(DirectorInput input){ return repository.create(input); }
    @Override
    public DirectorResponse update(Long id, DirectorInput input){ return repository.update(id, input); }
    @Override
    public boolean delete(Long id){ return repository.delete(id); }
}
