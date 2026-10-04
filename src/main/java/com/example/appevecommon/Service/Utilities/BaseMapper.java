package com.example.appevecommon.Service.Utilities;

import com.example.appevecommon.Models.Base.BaseEntity;

public abstract class BaseMapper<E,D> {
    public abstract D toDto(E entity);

    protected Long idOf(BaseEntity related) {
        return (related != null) ? related.getId() : null;
    }
}
