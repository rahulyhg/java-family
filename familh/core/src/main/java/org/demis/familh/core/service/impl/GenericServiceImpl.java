package org.demis.familh.core.service.impl;

import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;

import java.util.List;

public  abstract class GenericServiceImpl<M> implements GenericService<M> {

    @Override
    public abstract M create(M created) ;

    @Override
    public abstract M delete(Long id) throws ModelNotFoundException;

    @Override
    public abstract List<M> findAll();

    @Override
    public abstract List<M> findPart(int page, int size);

    @Override
    public abstract M findById(Long id);

    @Override
    public abstract M update(M updated) throws ModelNotFoundException;
}
