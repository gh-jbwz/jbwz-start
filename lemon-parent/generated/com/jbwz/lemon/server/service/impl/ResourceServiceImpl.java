package com.jbwz.lemon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.dao.ResourceDao;
import com.jbwz.lemon.server.base.AbstractBaseService;
import com.jbwz.lemon.server.service.ResourceService;
import com.jbwz.lemon.server.entity.Resource;

@Service
public class ResourceServiceImpl extends AbstractBaseService<Resource> implements ResourceService {


  @Autowired
  ResourceDao resourceDao;

  @Override
  protected BaseDao getDao() {
    return resourceDao;
  }
}




