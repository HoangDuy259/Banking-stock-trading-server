package com.example.demo.service.interfaces;

import java.util.List;

public interface IBaseService<T, ID> {
    T save(T entity);
    T findById(ID id);
    List<T> findAll();
    void delete(ID id);
}

