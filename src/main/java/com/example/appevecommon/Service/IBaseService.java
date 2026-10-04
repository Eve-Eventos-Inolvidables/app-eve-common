package com.example.appevecommon.Service;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Service.Utilities.PagedFilter;
import com.example.appevecommon.Service.Utilities.Responses.Ok.PageResult;

public interface IBaseService<
        E extends BaseEntity,
        D,
        F extends PagedFilter>
{ //D = DTO F = Filter

    D getById(Long id);
    E getEntity(Long id) ;

    //SOFT DELETE
    boolean delete(Long id);

    //Paged with optional filter
    default PageResult<D> getByFilter(F filter) {
        throw new UnsupportedOperationException("getByFilter() no implementado");
    }

}