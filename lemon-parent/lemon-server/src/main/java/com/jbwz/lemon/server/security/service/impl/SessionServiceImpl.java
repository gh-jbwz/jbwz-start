package com.jbwz.lemon.server.security.service.impl;

import com.jbwz.lemon.server.security.common.SessionUser;
import com.jbwz.lemon.server.security.common.SessionUtils;
import com.jbwz.lemon.server.security.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Override
    public void changeGrantedAuthority(HttpServletRequest request,
                                       Set<GrantedAuthority> authoritys) {
        SessionUser sessionUser = SessionUtils.getSessionUser();
        sessionUser.setAuthorities(authoritys);
        updateSessionUserAttribute(request);
    }


    public void updateSessionUserAttribute(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        HttpSession session = getSession(request);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        saveSession(session);
    }

    private void saveSession(HttpSession session) {
    }


    private HttpSession getSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        return httpSession;
    }
}
