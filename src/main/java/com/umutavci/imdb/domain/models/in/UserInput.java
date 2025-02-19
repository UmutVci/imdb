package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data

public class UserInput extends Base {
    private String username;
    private String email;
}
