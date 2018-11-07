package com.jbwz.lemon.server.message.sms.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 短信实体
 */
public class SMSRequest {

  private List<String> phoneList;
  private String smsContent;
  private String confirmDate;
  private String captcha;
  private String templateCode;
  private String signName = "";

  public SMSRequest() {
  }

  public SMSRequest(String smsContent, String phone) {
    this(null, smsContent, phone);
  }

  public SMSRequest(String captcha, String smsContent, String phone) {
    this.phoneList = Arrays.asList(phone);
    this.smsContent = smsContent;
    this.captcha = captcha;
  }

  /**
   * 组装短信
   *
   * @param captcha 验证码
   * @param smsContent 短信内容
   * @param phoneList 手机号列表
   */
  public SMSRequest(String captcha, String smsContent, List phoneList) {
    this.phoneList = phoneList;
    this.smsContent = smsContent;
    this.captcha = captcha;
  }

  /**
   * 组装短信
   *
   * @param smsContent 短信内容
   * @param phoneList 手机号列表
   */
  public SMSRequest(String smsContent, List phoneList) {
    this(null, smsContent, phoneList);
  }


  public String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }

  public String getSignName() {
    return signName;
  }

  public void setSignName(String signName) {
    this.signName = signName;
  }

  public List<String> getPhoneList() {
    return phoneList;
  }

  public void setPhoneList(List<String> phoneList) {
    this.phoneList = phoneList;
  }

  public String getSmsContent() {
    return smsContent;
  }

  public void setSmsContent(String smsContent) {
    this.smsContent = smsContent;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public String toString() {
    JSONObject message = new JSONObject();
    message.put("name", this.smsContent);
    message.put("code", this.captcha);
    message.put("bmsg", this.smsContent);
    message.put("confirmDate", this.confirmDate);
    return message.toJSONString();
  }

  public String getCommaPhones() {
    StringBuilder stringBuilder = new StringBuilder();
    phoneList.forEach((s) -> {
      stringBuilder.append(s);
      stringBuilder.append(",");
    });
    return stringBuilder.toString().replaceAll(",$", "");
  }

  public String getConfirmDate() {
    return confirmDate;
  }

  public void setConfirmDate(String confirmDate) {
    this.confirmDate = confirmDate;
  }
}
