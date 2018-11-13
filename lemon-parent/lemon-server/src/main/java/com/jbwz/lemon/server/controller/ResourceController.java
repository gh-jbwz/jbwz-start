package com.jbwz.lemon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.jbwz.lemon.server.entity.Resource;
import com.jbwz.lemon.server.service.ResourceService;
import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {
  
  @Autowired
  ResourceService resourceService;

  @RequestMapping(value = "/page")
  public ResponseJson list(Pageable pageable,Resource resource) {
    Page<Resource> pageList = resourceService.list(pageable,resource);
    return success(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(Resource resource) {
    resourceService.insert(resource);
    return success();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(Resource resource) {
    resourceService.updateById(resource);
    return success();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Integer id) {
    Resource resource = resourceService.findById(id);
    return success(resource);
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(Resource resource) {
    resourceService.deleteById(resource);
    return success(resource);
  }

}
