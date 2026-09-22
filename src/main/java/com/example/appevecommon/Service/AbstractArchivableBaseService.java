package com.example.appevecommon.Service;

import com.example.appevecommon.Models.Base.Archivable;
import com.example.appevecommon.Repository.IArchivableRepository;
import com.example.appevecommon.Service.Utilities.PagedFilter;

public abstract class AbstractArchivableBaseService<E extends Archivable, D, F extends PagedFilter>
        extends AbstractBaseService<E, D, F> {

    protected AbstractArchivableBaseService(IArchivableRepository<E> repository) {
        super(repository);
    }

    @Override
    public boolean delete(Long id) {
        IArchivableRepository<E> repo = (IArchivableRepository<E>) repository;
        repo.archive(id);
        return true;
    }
}