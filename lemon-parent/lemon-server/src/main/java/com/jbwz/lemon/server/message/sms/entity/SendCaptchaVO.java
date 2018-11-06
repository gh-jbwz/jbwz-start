package com.jbwz.lemon.server.message.sms.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class SendCaptchaVO implements Serializable {

  private static final long serialVersionUID = 1212L;

  //使用手机号注册
  @NotNull
  @Size(min = 11, max = 11)
  private String mobileNo;
  //密码
  @Size(max = 20)
  private String password;
  //验证码
  @Size(max = 10)
  private String captcha;

  //类型
  private Integer type;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }
}
