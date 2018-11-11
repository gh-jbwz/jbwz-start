package com.jbwz.lemon.server.controller;

import com.jbwz.lemon.server.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends BaseController {
    @GetMapping("/")
    public String root() {
        return "index.html";
    }

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }
}
