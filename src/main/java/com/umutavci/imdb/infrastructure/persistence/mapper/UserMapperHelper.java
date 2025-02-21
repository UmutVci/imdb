package com.umutavci.imdb.infrastructure.persistence.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapperHelper {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserMapperHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Named("login")
    public String passEncoder(String pass){
        return passwordEncoder.encode(pass);
    }
}
