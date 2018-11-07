package com.jbwz.lemon.server.security.common;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SessionUser implements UserDetails {

    private static long serialVersionUID = 991293242442L;

    // ~ Instance fields
    // ================================================================================================
    private Integer userId;
    private String password;
    private String username;
    private String realName;
    private Set<GrantedAuthority> authorities = new HashSet<>();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled = true;

    private String sessionId;
    private int loginErrorTimes;
    private Date lastLoginTime;

    public SessionUser() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public SessionUser(Integer userId, Long communityId, String password,
                       String username, String realName,
                       Set<GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.realName = realName;
        if (authorities != null) {
            this.authorities.addAll(authorities);
        }
    }

    public SessionUser(Integer userId, String password, String username, String realName,
                       Set<GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.realName = realName;
        this.authorities.addAll(authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("userId=");
        stringBuilder.append(this.userId);
        stringBuilder.append(",");
        stringBuilder.append("username=");
        stringBuilder.append(this.username);
        stringBuilder.append(",");
        stringBuilder.append("realName=");
        stringBuilder.append(this.realName);
        stringBuilder.append(",");
        stringBuilder.append("sessionId=");
        stringBuilder.append(this.sessionId);
        return stringBuilder.toString();
    }

    public int getLoginErrorTimes() {
        return loginErrorTimes;
    }

    public void setLoginErrorTimes(int loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
