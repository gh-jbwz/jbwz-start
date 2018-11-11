package com.jbwz.lemon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.jbwz.lemon.server.entity.User;
import com.jbwz.lemon.server.service.UserService;
import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
  
  @Autowired
  UserService userService;

  @RequestMapping(value = "/page")
  public ResponseJson list(Pageable pageable,User user) {
    Page<User> pageList = userService.list(pageable,user);
    return success(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(User user) {
    userService.insert(user);
    return success();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(User user) {
    userService.updateById(user);
    return success();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Integer id) {
    User user = userService.findById(id);
    return success(user);
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(User user) {
    userService.deleteById(user);
    return success(user);
  }

}
