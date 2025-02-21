package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.infrastructure.persistence.entities.User;
import com.umutavci.imdb.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomUserDetailsAdapter implements UserDetailsService {
    @Autowired
    private final UserJpaRepository userJpaRepository;

    public CustomUserDetailsAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJpaRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User couldnt find");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(), Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
    }
}
