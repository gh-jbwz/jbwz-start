package com.jbwz.lemon.server.message.sms.service;

import com.aliyuncs.exceptions.ClientException;
import com.jbwz.lemon.server.message.sms.entity.SMSTemplateCode;

import java.util.List;

/**
 * 发送短信接口
 *
 * @author yyh
 */
public interface SendSMSService {

  /**
   * @param smsTemplateCode 模板code 阿里云界面配置
   * @param captcha 验证码
   * @param content 消息内容
   * @param phoneList 手机号列表
   */
  String send(SMSTemplateCode smsTemplateCode, String captcha, String content,
              List<String> phoneList, String date) throws ClientException;

  /**
   * @param smsTemplateCode 模板code 阿里云界面配置
   * @param content 消息内容
   * @param phoneList 手机号列表
   */
  String send(SMSTemplateCode smsTemplateCode, String content, List<String> phoneList)
      throws ClientException;

  /**
   * @param smsTemplateCode 模板code 阿里云界面配置
   * @param captcha 验证码
   * @param content 消息内容
   * @param phone 手机号
   */
  String send(SMSTemplateCode smsTemplateCode, String captcha, String content,
              String phone) throws ClientException;

  /**
   * @param smsTemplateCode 模板code 阿里云界面配置
   * @param captcha 验证码
   * @param phone 手机号
   */
  String send(SMSTemplateCode smsTemplateCode, String captcha, String phone) throws ClientException;



}
