package com.example.appevecommon.Service;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Repository.IBaseRepository;
import com.example.appevecommon.Service.Utilities.Responses.PagedFilter;
import com.example.appevecommon.Service.Utilities.UpdatableMapper;

public abstract class UpdatableService
        <
        E extends BaseEntity,
        D, UD, //UpdateDto
        M extends UpdatableMapper<E,D,UD>,
        F extends PagedFilter
        >extends AbstractBaseService<E,D,M,F>  {

    protected UpdatableService(IBaseRepository<E> repository, M mapper) {
        super(repository, mapper);
    }


    public D update(Long id, UD updateDto) {
        return mapper.toDto(patch(id, entity -> mapper.updateEntityFromDto(updateDto, entity)));
    }
}
