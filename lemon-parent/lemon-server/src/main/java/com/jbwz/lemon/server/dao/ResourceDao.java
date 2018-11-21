package com.jbwz.lemon.server.dao;

import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceDao extends BaseDao<Resource> {

    List<Resource> findByTypeEqualsOrderByCreateTimeDesc(String s);
}




