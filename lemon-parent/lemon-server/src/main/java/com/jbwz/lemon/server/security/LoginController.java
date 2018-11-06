package com.jbwz.lemon.server.security;

import com.jbwz.lemon.server.security.common.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;

@Controller
public class LoginController extends BaseController {

  private static final int DEFAULT_APP_SESSION_TIMEOUT = 15 * 24 * 60 * 60;

  @Autowired
  BaseFormLoginService loginService;
  @Autowired
  SessionService sessionService;


  /**
   * login page name  must be loginView.html
   */
  @GetMapping("/login")
  public String loginGet(HttpServletRequest request) {
    if (isAppLogin(request)) {
      return "forward:login-app";
    }
    return "loginView";
  }

  @GetMapping(value = "/login-app")
  @ResponseBody
  public ResponseJson loginGetApp() {
    return responseFail(ResponseCode.ACCOUNT_NOT_LOGINED);
  }

  @ResponseBody
  @RequestMapping("/sein")
  public ResponseJson sessionInvalid() {
    return responseFail(1111, "session invalid");
  }

  @ResponseBody
  @RequestMapping("/login-success")
  public ResponseJson loginSuccess(LoginDataVO loginDataVO, HttpServletRequest request) {
    saveSessionIdToSessionUser(request);
    setSessionTimeOut(request);
    return responseSuccess(loginService.doLoginSuccess(loginDataVO));
  }


  @PostMapping("/login")
  public String loginPostSuccess(LoginDataVO loginDataVO, HttpServletRequest request) {
    return "forward:login-success";
  }

  @RequestMapping("/logout-success")
  public String logoutSuccess(HttpServletRequest request) {
    if (isAppLogin(request)) {
      return "forward:logout-success-app";
    }
    return "redirect:login";
  }

  @ResponseBody
  @RequestMapping("/logout-success-app")
  public ResponseJson logoutSuccessApp() {
    return responseSuccess();
  }

  @ResponseBody
  @RequestMapping("/login-error")
  public ResponseJson loginError(LoginDataVO loginDataVO, HttpServletRequest request) {
    Exception exception = (Exception) request.getAttribute(AUTHENTICATION_EXCEPTION);
    int code = ResponseCode.ACCOUNT_DELETED;
    if (exception != null) {
      if (exception instanceof DisabledException) {
        code = ResponseCode.ACCOUNT_DISABLED;
      } else if (exception instanceof UsernameNotFoundException) {
        code = ResponseCode.ACCOUNT_UNKNOW;
      } else if (exception instanceof LockedException) {
        code = ResponseCode.ACCOUNT_LOCKED;
      } else if (exception instanceof BadCredentialsException) {
        code = ResponseCode.ACCOUNT_WRONG_PASSWORD;
      }
      loginService.doLoginError(code, loginDataVO);
    }
    return fail(code);
  }


  private void setSessionTimeOut(HttpServletRequest request) {
      HttpSession session = getRequestSession(request);
      int timeout = getSessionTimeout();
      session.setMaxInactiveInterval(timeout);
  }

  private void saveSessionIdToSessionUser(HttpServletRequest request) {
    SessionUser sessionUser = getSessionUser();
    sessionUser.setSessionId(getRequestSession(request).getId());
  }

  private int getSessionTimeout() {
    return DEFAULT_APP_SESSION_TIMEOUT;
  }

  private HttpSession getRequestSession(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    return session;
  }


}
