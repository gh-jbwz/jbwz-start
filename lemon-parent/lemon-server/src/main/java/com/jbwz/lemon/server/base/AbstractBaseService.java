package com.jbwz.lemon.server.base;

public abstract class AbstractBaseService<T> implements BaseService<T> {

    protected abstract BaseDao getDao();
}
