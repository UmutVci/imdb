package com.umutavci.imdb.domain.ports.repositories;


import com.umutavci.imdb.domain.models.Base;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<INPUT, OUTPUT extends Base>{
    OUTPUT getSingle(Long id);
    List<OUTPUT> getAll();
    OUTPUT create(INPUT input);
    OUTPUT update(Long id, INPUT input);
    boolean delete(Long id);
}
