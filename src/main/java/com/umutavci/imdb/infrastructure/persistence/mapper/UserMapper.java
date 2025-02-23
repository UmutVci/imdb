package com.umutavci.imdb.infrastructure.persistence.mapper;

import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.UserResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class UserMapper {
    @Autowired
    private UserMapperHelper userMapperHelper;

    public User toUser(UserInput input) {
        if ( input == null ) {
            return null;
        }

        User user = new User();

        user.setPass( userMapperHelper.passEncoder( input.getPass() ) );
        user.setCreatedAt( input.getCreatedAt() );
        user.setUpdatedAt( input.getUpdatedAt() );
        user.setUsername( input.getUsername() );
        user.setEmail( input.getEmail() );

        return user;
    }

    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setCreatedAt( user.getCreatedAt() );
        userResponse.setUpdatedAt( user.getUpdatedAt() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setEmail( user.getEmail() );

        return userResponse;
    }
}


