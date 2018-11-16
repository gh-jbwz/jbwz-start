package com.jbwz.lemon.server.service.impl;

import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.dao.UserDao;
import com.jbwz.lemon.server.entity.User;
import com.jbwz.lemon.server.security.common.SessionUtils;
import com.jbwz.lemon.server.security.service.AbstractFormLoginService;
import com.jbwz.lemon.server.service.UserService;
import com.jbwz.lemon.server.vo.LoginDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl extends AbstractFormLoginService implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    protected BaseDao getDao() {
        return userDao;
    }

    @Override
    protected UserDetails login(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserNo(username);
        if (user == null) {
            throw new UsernameNotFoundException("no user");
        }
        Set<GrantedAuthority> aset = new HashSet<GrantedAuthority>();
        aset.add(new SimpleGrantedAuthority("/**"));
        return createSessionUser(user, aset);
    }

    @Override
    protected void updateAccountStatus(String status, Integer currentUserId) {

    }

    @Override
    protected void updateLoginTimeAndErrorTimes(Date date, int errorTimes, Integer currentUserId) {

    }

    @Override
    protected Object loginSuccess(LoginDataVO loginDataVO) {
        return userDao.findById(SessionUtils.getSessionUser().getUserId()).get();
    }

    @Override
    protected void loginFail(LoginDataVO loginDataVO) {

    }


}
