package com.jbwz.lemon.server.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lemon.mybatis.dao.BaseDao;
import com.lemon.account.dao.UserDao;
import com.lemon.mybatis.service.BaseService;
import com.lemon.account.service.UserService;
import com.jbwz.lemon.server.entity.User;

@Service
public class UserServiceImp extends BaseService<User> implements UserService {

  private static final Logger LOGGER = LogManager.getLogger(UserServiceImp.class);
  
  @Autowired
  UserDao userDao;

  @Override
  protected BaseDao getDao() {
    return userDao;
  }
}




