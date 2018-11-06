package com.jbwz.lemon.server.message.sms.entity;

public class EmailTemplate {

  /**
   * 发送给谁
   */
  private String[] toUser;
  /**
   * 内容
   */
  private String content;
  /**
   * 标题
   */
  private String subject;

  public String[] getToUser() {
    return toUser;
  }

  public String getContent() {
    return content;
  }

  public String getSubject() {
    return subject;
  }

  public void setToUser(String[] toUser) {
    this.toUser = toUser;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
}
