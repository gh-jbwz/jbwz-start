package com.jbwz.lemon.server.message.sms.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.jbwz.lemon.server.message.sms.dao.SendSMSDao;
import com.jbwz.lemon.server.message.sms.entity.SMSRequest;
import com.jbwz.lemon.server.message.sms.entity.SMSTemplateCode;
import com.jbwz.lemon.server.message.sms.service.SendSMSService;
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


    public long getExpireTime() {
        return defaultExpireTime;
    }
}
