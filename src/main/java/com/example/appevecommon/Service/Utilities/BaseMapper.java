package com.example.appevecommon.Service.Utilities;

import com.example.appevecommon.Models.Base.BaseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BaseMapper<E,D> {
    public abstract D toDto(E entity);
    List<D> toDtoList(List<E> entityList) {throw new UnsupportedOperationException("Not implemented yet"); }
    List<E> toEntityList(List<D> dtoList) {throw new UnsupportedOperationException("Not implemented yet");  }
    protected Long idOf(BaseEntity related) {
        return (related != null) ? related.getId() : null;
    }
}
