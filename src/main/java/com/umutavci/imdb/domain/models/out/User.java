package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data
public class User extends Base {
    private String username;
    private String email;
}
