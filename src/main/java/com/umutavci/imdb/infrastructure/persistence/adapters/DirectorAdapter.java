package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.DirectorInput;
import com.umutavci.imdb.domain.models.out.DirectorResponse;
import com.umutavci.imdb.domain.ports.repositories.IDirectorRepository;
import com.umutavci.imdb.infrastructure.persistence.entities.Director;
import com.umutavci.imdb.infrastructure.persistence.mapper.DirectorMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.DirectorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class DirectorAdapter implements IDirectorRepository {
    @Autowired
    private final DirectorJpaRepository directorJpaRepository;
    @Autowired
    private final DirectorMapper directorMapper;

    public DirectorAdapter(DirectorJpaRepository directorJpaRepository, DirectorMapper directorMapper) {
        this.directorJpaRepository = directorJpaRepository;
        this.directorMapper = directorMapper;
    }

    @Override
    public DirectorResponse getSingle(Long id) {
        Director director = directorJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Director couldnt find"));
        return directorMapper.toDirectorResponse(director);
    }

    @Override
    public List<DirectorResponse> getAll() {
        return directorJpaRepository.findAll()
                .stream()
                .map(directorMapper::toDirectorResponse)
                .toList();
    }

    @Override
    public DirectorResponse create(DirectorInput directorInput) {
        Director director = directorMapper.toDirector(directorInput);
        director.setCreatedAt(LocalDateTime.now());
        director.setUpdatedAt(LocalDateTime.now());
        Director savedDirector = directorJpaRepository.save(director);
        return directorMapper.toDirectorResponse(savedDirector);
    }

    @Override
    public DirectorResponse update(Long id, DirectorInput directorInput) {
        Director director = directorJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Director couldnt find"));
        director.setBirthDate(directorInput.getBirthDate());
        director.setName(directorInput.getName());
        director.setUpdatedAt(LocalDateTime.now());
        Director savedDirector = directorJpaRepository.save(director);
        return directorMapper.toDirectorResponse(savedDirector);
    }

    @Override
    public boolean delete(Long id) {
        Director director = directorJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Director couldnt find"));
        directorJpaRepository.delete(director);
        return !directorJpaRepository.findById(id).isPresent();
    }
}
