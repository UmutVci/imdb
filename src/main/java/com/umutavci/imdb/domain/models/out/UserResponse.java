package com.umutavci.imdb.domain.models.out;

import lombok.Data;

@Data
public class UserResponse extends ResponseBase {
    private String username;
    private String email;
}
