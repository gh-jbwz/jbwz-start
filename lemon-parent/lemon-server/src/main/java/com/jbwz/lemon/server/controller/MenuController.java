package com.jbwz.lemon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.jbwz.lemon.server.entity.Menu;
import com.jbwz.lemon.server.service.MenuService;
import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
  
  @Autowired
  MenuService menuService;

  @RequestMapping(value = "/page")
  public ResponseJson list(Pageable pageable,Menu menu) {
    Page<Menu> pageList = menuService.list(pageable,menu);
    return success(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(Menu menu) {
    menuService.insert(menu);
    return success();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(Menu menu) {
    menuService.updateById(menu);
    return success();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Integer id) {
    Menu menu = menuService.findById(id);
    return success(menu);
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(Menu menu) {
    menuService.deleteById(menu);
    return success(menu);
  }

}
