package com.example.demo.service;

import com.example.demo.repository.interfaces.IBaseRepository;
import com.example.demo.service.interfaces.IBaseService;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public abstract class BaseService<T, ID> implements IBaseService<T, ID> {

    protected abstract IBaseRepository<T, ID> getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }
}

