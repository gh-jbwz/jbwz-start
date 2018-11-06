package com.jbwz.lemon.server.security.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtils {

  public static Authentication getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication;
  }

  public static SessionUser getSessionUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
    return sessionUser;
  }
}
