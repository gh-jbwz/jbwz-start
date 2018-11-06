package com.jbwz.lemon.server.security.service;

import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface SessionService {

  void changeGrantedAuthoritys(HttpServletRequest request,
                               Set<GrantedAuthority> authoritys);


}
