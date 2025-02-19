package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.UserResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.User;
import com.umutavci.imdb.infrastructure.persistence.mapper.UserMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserAdapter implements ICRUDAdapter<UserInput, UserResponse> {

    @Autowired
    private final UserJpaRepository userJpaRepository;

    @Autowired
    private final UserMapper userMapper;

    public UserAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse getSingle(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse create(UserInput userInput) {
        User user = userMapper.toUser(userInput);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userJpaRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse update(Long id, UserInput userInput) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userInput.getUsername());
        user.setEmail(userInput.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userJpaRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public boolean delete(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userJpaRepository.delete(user);
        return !userJpaRepository.findById(id).isPresent();
    }
}

