package com.jbwz.lemon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.jbwz.lemon.server.entity.Dict;
import com.jbwz.lemon.server.service.DictService;
import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
  
  @Autowired
  DictService dictService;

  @RequestMapping(value = "/page")
  public ResponseJson list(Pageable pageable,Dict dict) {
    Page<Dict> pageList = dictService.list(pageable,dict);
    return success(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(Dict dict) {
    dictService.insert(dict);
    return success();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(Dict dict) {
    dictService.updateById(dict);
    return success();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Integer id) {
    Dict dict = dictService.findById(id);
    return success(dict);
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(Dict dict) {
    dictService.deleteById(dict);
    return success(dict);
  }

}
