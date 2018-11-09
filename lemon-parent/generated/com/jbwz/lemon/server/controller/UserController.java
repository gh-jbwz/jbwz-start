package com.jbwz.lemon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jbwz.lemon.server.entity.User;
import com.lemon.account.service.UserService;
import com.lemon.common.base.BaseController;
import com.lemon.common.base.PageList;
import com.lemon.common.base.ResponseJson;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
  
  @Autowired
  UserService userService;

  @RequestMapping(value = "/page")
  public ResponseJson list(User user) {
    PageList<User> pageList = userService.list(user);
    return responseSuccess(pageList);
  }
  
  @RequestMapping(value = "/save")
  public ResponseJson save(User user) {
    userService.insert(user);
    return responseSuccess();
  }

  @RequestMapping(value = "/update")
  public ResponseJson update(User user) {
    userService.updateById(user);
    return responseSuccess();
  }

  @RequestMapping(value = "/detail")
  public ResponseJson findById(@RequestParam("id") Long id) {
    User user = userService.findById(id);
    return responseSuccess(user);
  }

  @RequestMapping(value = "/delete")
  public ResponseJson delete(User user) {
    userService.deleteById(user);
    return responseSuccess(user);
  }

}
