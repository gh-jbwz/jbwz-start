package com.jbwz.lemon.server.vo;

import lombok.Data;

@Data
public class LoginDataVO {
    private String username;
    private String password;
    private String captcha;

}
