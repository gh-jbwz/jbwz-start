package com.jbwz.lemon.server.security.service.impl;

import com.jbwz.lemon.server.security.common.SessionUser;
import com.jbwz.lemon.server.security.common.SessionUtils;
import com.jbwz.lemon.server.security.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Service
public class SessionServiceImpl implements SessionService {

  private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);
  @Autowired
  SessionRepository sessionRepository;


  @Override
  public void changeGrantedAuthoritys(HttpServletRequest request,
                                      Set<GrantedAuthority> authoritys) {
    SessionUser sessionUser = SessionUtils.getSessionUser();
    sessionUser.setAuthorities(authoritys);
    updateSessionUserAttribute(request);
  }



  public void updateSessionUserAttribute(HttpServletRequest request) {
    SecurityContext context = SecurityContextHolder.getContext();
    Session session = getSession(request);
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
    saveSession(session);
  }

  private void saveSession(Session session) {
    sessionRepository.save(session);
  }

  private Session getSession(String sessionId) {
    Session session = (Session) sessionRepository.findById(sessionId);
    return session;
  }

  private Session getSession(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(false);
    Session session = getSession(httpSession.getId());
    return session;
  }
}
