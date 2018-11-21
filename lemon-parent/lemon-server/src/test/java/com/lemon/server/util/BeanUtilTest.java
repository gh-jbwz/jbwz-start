package com.lemon.server.util;

import com.alibaba.fastjson.JSONObject;
import com.jbwz.lemon.server.entity.User;
import com.jbwz.lemon.server.util.BeanUtil;
import org.junit.Test;

public class BeanUtilTest {
    @Test
    public void setDefaultValueForNull() throws IllegalAccessException {
        User user = new User();
        user.setUserName("hahah");
        user.setPassword("skfjlaj");
        user.setEmail("哈哈哈哈");

        User user22 = new User();
        user22 = BeanUtil.handleNullField(user);

        System.out.println(JSONObject.toJSON(user22));
    }
}
