package com.jbwz.lemon.server.controller;

import com.jbwz.lemon.server.base.BaseController;
import com.jbwz.lemon.server.base.ResponseCode;
import com.jbwz.lemon.server.base.ResponseJson;
import com.jbwz.lemon.server.security.common.SessionUser;
import com.jbwz.lemon.server.security.common.SessionUtils;
import com.jbwz.lemon.server.security.service.AbstractFormLoginService;
import com.jbwz.lemon.server.security.service.SessionService;
import com.jbwz.lemon.server.vo.LoginDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;

@Controller
public class LoginController extends BaseController {

    private static final int DEFAULT_APP_SESSION_TIMEOUT = 15 * 24 * 60 * 60;

    @Autowired
    AbstractFormLoginService loginService;
    @Autowired
    SessionService sessionService;

    @ResponseBody
    @RequestMapping("/login-success")
    public ResponseJson loginSuccess(LoginDataVO loginDataVO, HttpServletRequest request) {
        saveSessionIdToSessionUser(request);
        setSessionTimeOut(request);
        return success(loginService.doLoginSuccess(loginDataVO));
    }

    @ResponseBody
    @GetMapping("/login")
    public ResponseJson loginGet(LoginDataVO loginDataVO, HttpServletRequest request) {
        return fail(ResponseCode.NOT_LOGIN);
    }


    @RequestMapping("/logout-success")
    public ResponseJson logoutSuccess(HttpServletRequest request) {
        return success();
    }

    @ResponseBody
    @RequestMapping("/login-error")
    public ResponseJson loginError(LoginDataVO loginDataVO, HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute(AUTHENTICATION_EXCEPTION);
        int code = ResponseCode.ACCOUNT_WRONG_PASSWORD;
        if (exception != null) {
//            if (exception instanceof DisabledException) {
//                code = ResponseCode.ACCOUNT_DISABLED;
//            } else if (exception instanceof UsernameNotFoundException) {
//                code = ResponseCode.ACCOUNT_UNKNOWN;
//            } else if (exception instanceof LockedException) {
//                code = ResponseCode.ACCOUNT_LOCKED;
//            } else if (exception instanceof BadCredentialsException) {
//                code = ResponseCode.ACCOUNT_WRONG_PASSWORD;
//            }
            //TODO 用户不存在需要单独处理,不然没有sessionUser会报错
//            loginService.doLoginError(code, loginDataVO);
        }
        return fail(code);
    }


    private void setSessionTimeOut(HttpServletRequest request) {
        HttpSession session = getRequestSession(request);
        int timeout = getSessionTimeout();
        session.setMaxInactiveInterval(timeout);
    }

    private void saveSessionIdToSessionUser(HttpServletRequest request) {
        SessionUser sessionUser = SessionUtils.getSessionUser();
        sessionUser.setSessionId(getRequestSession(request).getId());
    }

    private int getSessionTimeout() {
        return DEFAULT_APP_SESSION_TIMEOUT;
    }

    private HttpSession getRequestSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
//        if (session == null)
//            session = request.getSession(true);
        return session;
    }


}
