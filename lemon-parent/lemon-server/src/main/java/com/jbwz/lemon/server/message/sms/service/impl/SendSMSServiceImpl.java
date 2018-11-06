package com.jbwz.lemon.server.message.sms.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SendSMSServiceImpl implements SendSMSService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendSMSServiceImpl.class);

  @Autowired
  SendSMSDao sendSMSDao;
  @Autowired
  CacheService cacheService;

  private static final String SIGN_NAME = "";
  private static final String RESPONSE_OK = "OK";
  /**
   * 默认过期时间 单位/秒
   */
  private static final long defaultExpireTime = 300L;

  @Override
  public String send(SMSTemplateCode smsTemplateCode, String captcha, String content,
      List<String> phoneList, String date) throws ClientException {
    SMSRequest smsRequest = new SMSRequest();
    smsRequest.setTemplateCode(smsTemplateCode.code());
    smsRequest.setSignName(SIGN_NAME);
    smsRequest.setCaptcha(captcha);
    smsRequest.setConfirmDate(date);
    smsRequest.setSmsContent(content);
    smsRequest.setPhoneList(phoneList);
    String code;
    try {
      SendSmsResponse smsResponse = sendSMSDao.send(smsRequest);
      code = smsResponse.getCode();
      if (!RESPONSE_OK.equals(code)) {
        LOGGER.debug("短信发送失败：code:{} msg:{}", smsResponse.getCode(), smsResponse.getMessage());
        throw new ClientException(smsResponse.getMessage());
      }
    } catch (ClientException e) {
      LOGGER.error("发送短信异常", e);
      throw e;
    }
    return code;
  }


  @Override
  public String send(SMSTemplateCode smsTemplateCode, String content, List<String> phoneList)
      throws ClientException {
    return send(smsTemplateCode, null, content, phoneList, null);
  }

  @Override
  public String send(SMSTemplateCode smsTemplateCode, String captcha, String content,
      String phone) throws ClientException {
    return send(smsTemplateCode, captcha, content, Arrays.asList(phone), null);
  }

  @Override
  public String send(SMSTemplateCode smsTemplateCode, String captcha, String phone)
      throws ClientException {
    return send(smsTemplateCode, captcha, null, Arrays.asList(phone), null);
  }

  @Override
  public String getCaptcha(MessageBusinessType type, String phone) {
    String cachedCaptcha = (String) cacheService.get(getCacheKey(type, phone));
    return cachedCaptcha;
  }

  @Override
  public String sendCaptchaMsg(String phone, MessageBusinessType type)
      throws CaptchaNotUsedException, ClientException {
    String captcha = RandomUtil.nextNumberCaptcha();
    if (StringUtils.isNotBlank(getCaptcha(type, phone))) {
      throw new CaptchaNotUsedException();
    }
    cacheService.put(getCacheKey(type, phone), captcha, getExpireTime());
    send(SMSTemplateCode.CAPTCHA_CODE, captcha, phone);
    return captcha;
  }

  /**
   * 校验验证码
   *
   * @param captcha 验证码
   * @param phone 手机号
   * @param type 业务类型，登录，注册，重置密码等
   * @throws CaptchaExpireException 验证码过期
   */
  @Override
  public boolean matchCaptcha(String captcha, String phone, MessageBusinessType type)
      throws CaptchaExpireException, CaptchaWrongException {
    //TODO should be removed after test
    // test begin
    if (true) {
      return true;
    }
    // test end
    String cacheCaptcha = getCaptcha(type, phone);
    LOGGER.info("手机校验验证码的手机号：{},输入的验证码：{},真正的验证码：{},业务名称为：{}", phone, captcha, cacheCaptcha,
        type.getType());
    if (StringUtils.isBlank(cacheCaptcha)) {
      throw new CaptchaExpireException();
    }
    if (!captcha.equals(cacheCaptcha)) {
      throw new CaptchaWrongException();
    }
    return true;
  }

  private String getCacheKey(MessageBusinessType type, String phone) {
    return CacheKeys.SMS_MESSAGE_PREFIX + CacheKeys.SEPARATOR + type.name() + CacheKeys.SEPARATOR
        + phone;
  }

  public long getExpireTime() {
    return defaultExpireTime;
  }
}
