package com.jbwz.lemon.server.controller;

import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;
import com.jbwz.lemon.server.entity.User;
import com.jbwz.lemon.server.security.common.SessionUtils;
import com.jbwz.lemon.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/page")
    public ResponseJson list(HttpServletRequest request, Pageable pageable, User user) {
        System.out.println(request.getSession(false).getId());
        System.out.println(SessionUtils.getSessionUser().getSessionId());

        Page<User> pageList = userService.list(pageable, user);
        return success(pageList);
    }

    @RequestMapping(value = "/save")
    public ResponseJson save(User user) {
        user.setPassword(user.getUserNo());
        userService.insert(user);
        return success();
    }

    @RequestMapping(value = "/update")
    public ResponseJson update(User user) {
        userService.updateById(user);
        return success();
    }

    @RequestMapping(value = "/detail/{id}")
    public ResponseJson findById(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        return success(user);
    }

    @RequestMapping(value = "/delete")
    public ResponseJson delete(User user) {
        userService.deleteById(user);
        return success(user);
    }

}
