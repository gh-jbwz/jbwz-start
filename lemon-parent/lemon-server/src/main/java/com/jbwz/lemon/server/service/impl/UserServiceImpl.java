package com.jbwz.lemon.server.service.impl;

import com.jbwz.lemon.server.base.BaseDao;
import com.jbwz.lemon.server.dao.UserDao;
import com.jbwz.lemon.server.security.service.AbstractFormLoginService;
import com.jbwz.lemon.server.service.UserService;
import com.jbwz.lemon.server.util.DateUtil;
import com.jbwz.lemon.server.vo.LoginDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl extends AbstractFormLoginService implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    protected BaseDao getDao() {
        return userDao;
    }

    @Override
    protected UserDetails login(String username) {
        return createSessionUser(1, username, "", "", "", DateUtil.nowDateTime(), 1);
    }

    @Override
    protected void updateAccountStatus(String status, Integer currentUserId) {

    }

    @Override
    protected void updateLoginTimeAndErrorTimes(Date date, int errorTimes, Integer currentUserId) {

    }

    @Override
    protected Object loginSuccess(LoginDataVO loginDataVO) {
        return null;
    }

    @Override
    protected void loginFail(LoginDataVO loginDataVO) {

    }


}
