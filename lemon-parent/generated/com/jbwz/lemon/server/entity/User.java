package com.jbwz.lemon.server.entity;

import java.util.Date;
import javax.persistence.*;
import tk.mybatis.mapper.annotation.KeySql;

@Table(name = "jbwz_user")
public class User {
    // 用户表
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer userId;

    // 员工号
    private String userNo;

    // 用户名称
    private String userName;

    // 昵称
    private String nickName;

    // 密码
    private String password;

    // 头像
    private String userAvater;

    // 性别
    private String gender;

    // 邮箱
    private String email;

    // 座机
    private String tellNo;

    // 移动电话
    private String mobile;

    // 生日
    private Date birthday;

    // 状态 0-可用 1-锁定 9 删除
    private String status;

    // 创建人
    private Integer createBy;

    // 创建时间
    private Date createTime;

    // 更新人
    private Integer updateBy;

    // 更新时间
    private Date updateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAvater() {
        return userAvater;
    }

    public void setUserAvater(String userAvater) {
        this.userAvater = userAvater;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTellNo() {
        return tellNo;
    }

    public void setTellNo(String tellNo) {
        this.tellNo = tellNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}