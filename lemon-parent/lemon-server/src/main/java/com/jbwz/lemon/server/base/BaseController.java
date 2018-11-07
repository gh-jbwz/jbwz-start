package com.jbwz.lemon.server.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BaseController {
    @Autowired
    MessageSource messageSource;

    protected ResponseJson success() {
        return result(ResponseCode.SUCCESS_CODE);
    }

    protected ResponseJson success(Object data) {
        return result(ResponseCode.SUCCESS_CODE, data);
    }

    private ResponseJson result(int code) {
        return result(code, null);
    }

    private ResponseJson result(int code, Object data) {
        //TODO 通过国际化获取错误信息
        return new ResponseJson(code, messageSource.getMessage(String.valueOf(code), null, Locale.CHINESE), data);
    }

    protected ResponseJson fail(int code) {
        return result(code);
    }

    protected ResponseJson fail(int code, Object data) {
        return result(code, data);
    }

    protected ResponseJson error(Object data) {
        return result(ResponseCode.ERROR_CODE, data);
    }
}
