package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data
public class LoginInput extends Base {
    private String email;
    private String pass;
}
