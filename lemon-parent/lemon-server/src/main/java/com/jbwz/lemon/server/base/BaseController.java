package com.jbwz.lemon.server.base;

public class BaseController {

    protected ResponseJson success(Object data) {
        return result(ResponseCode.SUCCESS_CODE, data);
    }

    protected ResponseJson result(int code, Object data) {
        //TODO 通过国际化获取错误信息
        return new ResponseJson(code, "", data);
    }

    protected ResponseJson fail(int code) {
        return result(code, "");
    }

    protected ResponseJson error(Object data) {
        return result(ResponseCode.ERROR_CODE, data);
    }
}
