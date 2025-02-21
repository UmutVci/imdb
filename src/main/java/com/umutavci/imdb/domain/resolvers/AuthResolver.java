package com.umutavci.imdb.domain.resolvers;

import com.umutavci.imdb.domain.models.in.LoginInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class AuthResolver {

    private final AuthenticationManager authenticationManager;

    public AuthResolver(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @MutationMapping
    public boolean login(@Argument LoginInput input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPass())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication.isAuthenticated();
    }

    @QueryMapping
    public String me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Anonym User";
        }
        return authentication.getName();
    }
}
