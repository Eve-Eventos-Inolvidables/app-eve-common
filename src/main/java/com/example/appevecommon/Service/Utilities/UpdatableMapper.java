package com.example.appevecommon.Service.Utilities;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


public interface UpdatableMapper<E, D, UD> extends ReadableMapper<E, D> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UD updateDto, @MappingTarget E entity);
}