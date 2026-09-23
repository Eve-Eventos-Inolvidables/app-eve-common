package com.example.appevecommon.Service;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Repository.IBaseRepository;
import com.example.appevecommon.Service.Utilities.PagedFilter;
import com.example.appevecommon.Service.Utilities.Responses.PageResult;
import com.example.appevecommon.Service.Exception.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public abstract class AbstractBaseService<E extends BaseEntity, D, F extends PagedFilter>
        implements IBaseService<E, D, F> {

    private static final int DEFAULT_SIZE = 20;

    protected final IBaseRepository<E> repository;

    protected AbstractBaseService(IBaseRepository<E> repository) {
        this.repository = repository;
    }

    public abstract D toDto(E entity);

    public abstract E toEntity(D dto);

    public abstract Specification<E> toSpecification(F filter);

    @Override
    public D create(D dto) {
        E entity = repository.save(toEntity(dto));
        return toDto(entity);
    }


    @Override
    public E getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public D getById(Long id) {
        return toDto(getEntity(id));
    }

    @Override
    public D update(Long id, D dto) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        E entity = toEntity(dto);
        entity.setId(id);
        return toDto(repository.save(entity));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<D> getAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public PageResult<D> getByFilter(F filter) {
        Pageable pageable = (filter != null) ? filter.toPageable() : PageRequest.of(0, DEFAULT_SIZE);
        return PageResult.from(repository.findAll(toSpecification(filter), pageable).map(this::toDto));
    }
}