package com.jbwz.lemon.server.message.sms.entity;

/**
 * 短信模板
 *
 * @author yyh
 */
public enum SMSTemplateCode {
  CAPTCHA_CODE("SMS_125019786", 1, "验证码短信"), TODO_RESERVE_NOTIFY_CODE("SMS_128890371", 2,
      "收房待办事项通知");

  private String code;
  private String comment;
  private int type;

  /**
   * @param code 阿里生成的模板code
   * @param type 类型 1:只发送验证码，2：只发送通知，3：既有验证码又有通知
   * @param comment 说明
   */
  SMSTemplateCode(String code, int type, String comment) {
    this.code = code;
    this.type = type;
    this.comment = comment;
  }

  public String code() {
    return this.code;
  }

  public int type() {
    return this.type;
  }
}
