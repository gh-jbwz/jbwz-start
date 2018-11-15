package com.jbwz.lemon.server.dao;

import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User> {

    User findByUserNo(String userno);
}




