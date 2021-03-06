package com.jbwz.lemon.server.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jbwz_user")
public class User {
    // 用户表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    // 员工号
    private String userNo;

    // 用户名称
    private String userName;

    // 昵称
    private String nickName;

    // 密码
    @JSONField(serialize = false)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    //入职时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date entryDate;

    // 状态 0-可用 1-锁定 9 删除
    private String status;

    // 创建人
    @JSONField(serialize = false)
    private Integer createBy;

    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 更新人
    @JSONField(serialize = false)
    private Integer updateBy;

    // 更新时间
    @JSONField(serialize = false)
    private Date updateTime;

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
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