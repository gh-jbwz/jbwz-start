package com.jbwz.lemon.server.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class AbstractBaseService<T> implements BaseService<T> {

    protected abstract BaseDao getDao();

    @Override
    public T insert(T t) {
        return (T) getDao().save(t);
    }

    public T updateById(T t) {
        return (T) getDao().save(t);
    }

    public void deleteById(T t) {
        getDao().delete(t);
    }

    public T findById(Integer id) {
        Optional<T> dto = getDao().findById(id);
        return dto.get();
    }

    public Page<T> list(Pageable pageable, T t) {
        return getDao().findAll(pageable);
//        return getDao().findAll(new Example() {
//            @Override
//            public Object getProbe() {
//                return null;
//            }
//
//            @Override
//            public ExampleMatcher getMatcher() {
//                ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//                //TODO  需要把根据条件动态查询
//                return exampleMatcher;
//            }
//        }, pageable);
    }
}
