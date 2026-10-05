package com.example.appevecommon.Service;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Repository.IBaseRepository;
import com.example.appevecommon.Service.Utilities.BaseMapper;
import com.example.appevecommon.Service.Utilities.PagedFilter;
import com.example.appevecommon.Service.Utilities.Responses.Ok.PageResult;
import com.example.appevecommon.Service.Exception.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractBaseService<
        E extends BaseEntity,
        D,
        M extends BaseMapper<E,D>,
        F extends PagedFilter>
        implements IBaseService<E, D, F> {

    private static final int DEFAULT_SIZE = 20;

    protected final IBaseRepository<E> repository;
    protected final M mapper;

    protected AbstractBaseService(IBaseRepository<E> repository,M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public abstract Specification<E> toSpecification(F filter);

    /**
     * Loads the entity, applies the changes and saves it.
     * Used by update() so untouched columns (id, archived, and any other
     * server-owned field) keep their current values. NEVER rebuild the entity
     * with toEntity() inside an update: that is a full replace and resets them.
     */
    protected E patch(Long id, Consumer<E> changes) {
        E entity = getEntity(id);
        changes.accept(entity);
        return repository.save(entity);
    }

    @Override
    public E getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public D getById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.archive(id);
        return true;
    }

    //if you want to implement getall, do it in a  specific implementation
    public List<D> getAll() {
        throw new UnsupportedOperationException("getAll() no implementado");
    }

    @Override
    public PageResult<D> getByFilter(F filter) {
        Pageable pageable = (filter != null) ? filter.toPageable() : PageRequest.of(0, DEFAULT_SIZE);
        return PageResult.from(repository.findAll(toSpecification(filter), pageable).map(mapper::toDto));
    }
}