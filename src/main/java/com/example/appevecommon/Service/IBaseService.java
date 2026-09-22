package com.example.appevecommon.Service;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Service.Utilities.Responses.PageResult;

import java.util.List;

public interface IBaseService <X extends BaseEntity, D, F > { //D = DTO F = Filter
    //CRUD
    D create(D dto);
    D getById(Long id);
    D update(Long id, D dto );
    boolean delete(Long id);

    //!!!USE ONLY WITH SMALL VOLUME TABLES
    default List<D> getAll() {
        throw new UnsupportedOperationException("getAll() no implementado");
    }

    //Paginated with filter(optional)
    default PageResult<D> getByFilter(Integer page, F filter) {
        throw new UnsupportedOperationException("getByFilter() no implementado");
    }

}
