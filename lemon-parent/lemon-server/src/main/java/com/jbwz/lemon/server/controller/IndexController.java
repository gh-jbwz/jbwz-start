package com.jbwz.lemon.server.controller;

import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends BaseController {
    @GetMapping("/")
    public ResponseJson index() {
        return success("hahaha");
    }
}
