package com.jbwz.lemon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.dao.MenuDao;
import com.jbwz.lemon.server.base.AbstractBaseService;
import com.jbwz.lemon.server.service.MenuService;
import com.jbwz.lemon.server.entity.Menu;

@Service
public class MenuServiceImpl extends AbstractBaseService<Menu> implements MenuService {


  @Autowired
  MenuDao menuDao;

  @Override
  protected BaseDao getDao() {
    return menuDao;
  }
}




