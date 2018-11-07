package com.jbwz.lemon.server.security.service;

import com.jbwz.lemon.server.base.ResponseCode;
import com.jbwz.lemon.server.constant.UserConstant;
import com.jbwz.lemon.server.security.common.SessionUser;
import com.jbwz.lemon.server.security.common.SessionUtils;
import com.jbwz.lemon.server.util.DateUtil;
import com.jbwz.lemon.server.vo.LoginDataVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractFormLoginService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFormLoginService.class);
    private static final int DEFAULT_MAX_LOGIN_ERROR_TIMES = 5;
    private static final int CLEAR_LOGIN_ERROR_TIMES = 0;
    private static final long DEFAULT_MAXIMUM_LOCK_TIME = 30 * 60 * 60 * 1000L;

    /**
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return login(username);
    }

    protected abstract UserDetails login(String username);

    protected SessionUser createSessionUser(Integer userId, String username, String password,
                                            String realName, String status, Date lastLoginTime, int errorTimes) {
        return createSessionUser(userId, username, password, realName, null, status,
                lastLoginTime, errorTimes);
    }


    protected SessionUser createSessionUser(Integer userId, String username,
                                            String password, String realName, Set<GrantedAuthority> grantedAuthorities,
                                            String status, Date lastLoginTime, int errorTimes) {
        SessionUser sessionUser = new SessionUser(userId, password, username, realName,
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

    protected String getUsername(UsernamePasswordAuthenticationToken token) {
        String username = (token.getPrincipal() == null) ? ""
                : token.getName();
        return username;
    }


    public Object doLoginSuccess(LoginDataVO loginDataVO) {
        updateLoginTimeAndErrorTimes(DateUtil.nowDateTime(), CLEAR_LOGIN_ERROR_TIMES, getCurrentUserId());
        return loginSuccess(loginDataVO);
    }

    public void doLoginError(int errorCode, LoginDataVO loginDataVO) {
        if (errorCode == ResponseCode.ACCOUNT_WRONG_PASSWORD
                || errorCode == ResponseCode.ACCOUNT_LOCKED) {
            int errorTimes = getSessionUser().getLoginErrorTimes();
            errorTimes++;
            updateLoginTimeAndErrorTimes(DateUtil.nowDateTime(), errorTimes, getCurrentUserId());
            if (errorTimes == getMaxErrorTimes()) {
                updateAccountStatus(UserConstant.ACCOUNT_LOCKED, getCurrentUserId());
            }
        }
        loginFail(loginDataVO);
    }

    protected abstract void updateAccountStatus(String status, Integer currentUserId);

    private boolean shouldUnlockAccount(SessionUser sessionUser) {
        Date lastDate = sessionUser.getLastLoginTime();
        Date nowDate = DateUtil.nowDateTime();
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
                                                         Integer currentUserId);

    protected int getMaxErrorTimes() {
        return DEFAULT_MAX_LOGIN_ERROR_TIMES;
    }

    protected abstract Object loginSuccess(LoginDataVO loginDataVO);

    protected abstract void loginFail(LoginDataVO loginDataVO);

    private SessionUser getSessionUser() {
        SessionUser sessionUser = SessionUtils.getSessionUser();
        return sessionUser;
    }

    private Integer getCurrentUserId() {
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
