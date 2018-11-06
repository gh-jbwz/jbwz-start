package com.jbwz.lemon.server.message.sms.service;

/**
 * 发送邮件
 *
 * @author yyh
 */
public interface EmailService {


  void sendSimpleEmail(String content, String title, String... toUser);
}
