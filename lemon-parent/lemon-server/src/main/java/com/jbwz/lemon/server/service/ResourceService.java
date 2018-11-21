package com.jbwz.lemon.server.service;

import com.jbwz.lemon.server.entity.Resource;
import com.jbwz.lemon.server.base.BaseService;

import java.util.List;

public interface ResourceService extends BaseService<Resource> {

    List<Resource> getAllMenuList();
}




