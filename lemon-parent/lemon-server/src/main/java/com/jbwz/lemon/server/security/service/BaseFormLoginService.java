package com.jbwz.lemon.server.security.service;

import com.jbwz.lemon.server.security.common.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseFormLoginService implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseFormLoginService.class);
  private static final int DEFAULT_MAX_LOGIN_ERROR_TIMES = 5;
  private static final int CLEAR_LOGIN_ERROR_TIMES = 0;
  private static final long DEFAULT_MAXIMUM_LOCK_TIME = 30 * 60 * 60 * 1000L;

  /**
   */
  @Deprecated
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    return null;
  }


  protected SessionUser createSessionUser( Long userId, String password,
                                          String username, String realName, String status, Date lastLoginTime, int errorTimes) {
    return createSessionUser( userId, null, password, username, realName, null, status,
        lastLoginTime, errorTimes);
  }

  protected SessionUser createSessionUser( Long userId, Long communityId,
      String password,
      String username, String realName, String status, Date lastLoginTime, int errorTimes) {
    return createSessionUser( userId, communityId, password, username, realName, null,
        status, lastLoginTime, errorTimes);
  }

  protected SessionUser createSessionUser( Long userId, Long communityId,
      String password, String username, String realName, Set<GrantedAuthority> grantedAuthorities,
      String status, Date lastLoginTime, int errorTimes) {
    SessionUser sessionUser = new SessionUser(userId, communityId, password, username, realName,
        grantedAuthorities);
    sessionUser.setLoginErrorTimes(errorTimes);
    sessionUser.setLastLoginTime(lastLoginTime);
    checkStatus(sessionUser, status);
    return sessionUser;
  }

  private void checkStatus(SessionUser sessionUser, String status) {
    if (!(UserConstant.ACCOUNT_ENABLE.equals(status))) {
      if (UserConstant.ACCOUNT_LOCKED.equals(status)) {
        if (!shouldUnlockAccount(sessionUser)) {
          sessionUser.setAccountNonLocked(true);
        }
      } else if (UserConstant.ACCOUNT_DISABLE.equals(status)) {
        sessionUser.setEnabled(false);
      }
    }
  }

  protected String getUsername(FormLoginToken token) {
    String username = (token.getPrincipal() == null) ? ""
        : token.getName();
    return username;
  }

  /**
   * login by token
   *
   * implemented by subclass
   */
  public UserDetails loadUserByToken(FormLoginToken token)
      throws UsernameNotFoundException {
    return null;
  }


  protected abstract Set<GrantedAuthority> getPublicPermission();


  public Object doLoginSuccess(LoginDataVO loginDataVO) {
    updateLoginTimeAndErrorTimes(DateUtil.nowDate(), CLEAR_LOGIN_ERROR_TIMES, getCurrentUserId());
    return loginSuccess(loginDataVO);
  }

  public void doLoginError(int errorCode, LoginDataVO loginDataVO) {
    if (errorCode == ResponseCode.ACCOUNT_WRONG_PASSWORD
        || errorCode == ResponseCode.ACCOUNT_LOCKED) {
      int errorTimes = getSessionUser().getLoginErrorTimes();
      errorTimes++;
      updateLoginTimeAndErrorTimes(DateUtil.nowDate(), errorTimes, getCurrentUserId());
      if (errorTimes == getMaxErrorTimes()) {
        updateAccountStatus(UserConstant.ACCOUNT_LOCKED, getCurrentUserId());
      }
    }
    loginFail(loginDataVO);
  }

  protected abstract void updateAccountStatus(String status, Long currentUserId);

  private boolean shouldUnlockAccount(SessionUser sessionUser) {
    Date lastDate = sessionUser.getLastLoginTime();
    Date nowDate = DateUtil.nowDate();
    boolean b = ((nowDate.getTime() - lastDate.getTime()) >= getMaximumLockTime());
    if (b) {
      updateAccountStatus(UserConstant.ACCOUNT_ENABLE, getCurrentUserId());
    }
    return b;
  }


  protected long getMaximumLockTime() {
    return DEFAULT_MAXIMUM_LOCK_TIME;
  }

  protected abstract void updateLoginTimeAndErrorTimes(Date date, int errorTimes,
      Long currentUserId);

  protected int getMaxErrorTimes() {
    return DEFAULT_MAX_LOGIN_ERROR_TIMES;
  }

  protected abstract Object loginSuccess(LoginDataVO loginDataVO);

  protected abstract void loginFail(LoginDataVO loginDataVO);

  private SessionUser getSessionUser() {
    SessionUser sessionUser = SessionUtils.getSessionUser();
    return sessionUser;
  }

  private Long getCurrentUserId() {
    SessionUser sessionUser = SessionUtils.getSessionUser();
    return sessionUser.getUserId();
  }

  protected Set<GrantedAuthority> getGrantedAuthority(String path) {
    Set<GrantedAuthority> grantedAuthorityList = new HashSet<>();
    String url = path;
    if (StringUtils.isNotBlank(url)) {
      String[] urlArray = url.split(",");
      for (String s : urlArray) {
        if (!s.startsWith("/")) {
          s = "/" + s;
        }
        grantedAuthorityList.add(new SimpleGrantedAuthority(s));
      }
    }
    return grantedAuthorityList;
  }
}
