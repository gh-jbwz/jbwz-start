package com.jbwz.lemon.server.controller;

import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;
import com.jbwz.lemon.server.entity.User;
import com.jbwz.lemon.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String root() {
        return "index.html";
    }

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @RequestMapping(value = "/register")
    public ResponseJson register(User user) {
        user.setPassword(user.getUserNo());
        userService.insert(user);
        return success();
    }
}
