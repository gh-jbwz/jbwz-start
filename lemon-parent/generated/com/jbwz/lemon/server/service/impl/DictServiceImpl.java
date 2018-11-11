package com.jbwz.lemon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.dao.DictDao;
import com.jbwz.lemon.server.base.AbstractBaseService;
import com.jbwz.lemon.server.service.DictService;
import com.jbwz.lemon.server.entity.Dict;

@Service
public class DictServiceImpl extends AbstractBaseService<Dict> implements DictService {


  @Autowired
  DictDao dictDao;

  @Override
  protected BaseDao getDao() {
    return dictDao;
  }
}




