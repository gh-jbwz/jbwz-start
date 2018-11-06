package com.jbwz.lemon.server.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * fastjson配置 全局配置
 *
 * @author yyh
 */
@Configuration
public class FastJsonSerializerConfig {

  /**
   * 修改spring boot 默认的json序列化为fastjson
   */
  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    //1.需要定义一个convert转换消息的对象;
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    //2:添加fastJson的配置信息;
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullNumberAsZero);
    fastJsonConfig.setSerializeFilters(this.jsonValueFilter);
    //3处理中文乱码问题
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    //4.在convert中添加配置信息.
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    // 暂不需要字符串转义
    StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
    stringHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    return new HttpMessageConverters(fastJsonHttpMessageConverter);

  }

  private static final String KEY_DATA = "data";
  private ValueFilter jsonValueFilter = new ValueFilter() {
    @Override
    public Object process(Object obj, String s, Object v) {
      if (v == null) {
        Object tmpObj = "";
        if (s.equals(KEY_DATA)) {
          tmpObj = new HashMap<>();
        }
        return tmpObj;
      }
      return v;
    }
  };
}
