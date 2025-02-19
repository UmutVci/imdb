package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.Base;

import java.util.List;

public interface ICRUDAdapter<INPUT, OUTPUT extends Base>{
    OUTPUT getSingle(Long id);
    List<OUTPUT> getAll();
    OUTPUT create(INPUT input);
    OUTPUT update(Long id, INPUT input);
    boolean delete(Long id);

}
