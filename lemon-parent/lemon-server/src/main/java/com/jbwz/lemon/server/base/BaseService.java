package com.jbwz.lemon.server.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BaseService<T> {
    T insert(T t);

    T updateById(T t);

    void deleteById(T t);

    T findById(Integer id);

    Page<T> list(Pageable pageable, T t);
}
