package com.jbwz.lemon.server.base;

import lombok.Data;

@Data
public class ResponseJson {
    private int code;
    private String msg;
    private Object data;

    public ResponseJson() {
    }

    public ResponseJson(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseJson(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
