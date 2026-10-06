package com.example.appevecommon.Service.Utilities;

import java.util.List;


public interface ReadableMapper<E, D> extends MapperBase {

    D toDto(E entity);

    List<D> toDtoList(List<E> entityList);
}