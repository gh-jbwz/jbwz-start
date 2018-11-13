package com.jbwz.lemon.server.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${jbwz.viewPath}")
    private String viewPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("file:" + viewPath);

    }

    /**
     * 修改spring boot 默认的json序列化为fastjson
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullNumberAsZero);
        fastJsonConfig.setSerializeFilters(this.jsonValueFilter);
        //3处理中文乱码问题
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        // 暂不需要字符串转义
        return fastJsonHttpMessageConverter;
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
