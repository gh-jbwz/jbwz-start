package com.jbwz.lemon.server.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T> extends JpaRepository<T,Integer> {
}
