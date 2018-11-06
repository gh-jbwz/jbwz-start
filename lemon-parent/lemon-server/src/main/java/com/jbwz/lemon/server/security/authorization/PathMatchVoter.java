package com.jbwz.lemon.server.security.authorization;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class PathMatchVoter implements AccessDecisionVoter<FilterInvocation> {

  private final AntPathMatcher matcher = new AntPathMatcher();

  private static final String AUTHENTICATED = "authenticated";

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  public boolean requireAuthorize(ConfigAttribute attribute) {
    return AUTHENTICATED.equals(attribute.toString());
  }


  @Override
  public int vote(Authentication authentication, FilterInvocation fi,
                  Collection<ConfigAttribute> attributes) {
    String requestPath = getRequestPath(fi.getRequest());

    if (authentication == null || !authentication.isAuthenticated()) {
      return ACCESS_DENIED;
    }

    int result = ACCESS_ABSTAIN;
    Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

    for (ConfigAttribute attribute : attributes) {
      if (this.requireAuthorize(attribute)) {
        result = ACCESS_DENIED;

        // Attempt to find a matching granted authority
        for (GrantedAuthority authority : authorities) {
          if (matcher.match(authority.getAuthority(), requestPath)) {
            return ACCESS_GRANTED;
          }
        }
      } else {
        return ACCESS_GRANTED;
      }
    }

    return result;
  }

  Collection<? extends GrantedAuthority> extractAuthorities(
      Authentication authentication) {
    return authentication.getAuthorities();
  }

  String getRequestPath(HttpServletRequest request) {
    return request.getServletPath();
  }
}
