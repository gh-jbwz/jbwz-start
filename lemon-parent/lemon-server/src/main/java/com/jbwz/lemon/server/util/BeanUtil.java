package com.jbwz.lemon.server.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    public static <T> T handleNullField(T t) {
        if (t != null) {
            Class tClass = t.getClass();
            Method[] methods = tClass.getDeclaredMethods();
            try {
                Map<String, Class> nullFields = new HashMap();
                for (Method method : methods) {
                    String methodType = "get";
                    String methodName = method.getName();
                    if (methodName.startsWith(methodType)) {
                        Object v = method.invoke(t);
                        Class returnType = method.getReturnType();
                        if (v == null) {
                            nullFields.put(methodName.replace(methodType, ""), returnType);
                        }
                    }
                }
                for (Method method : methods) {
                    String methodType = "set";
                    String methodName = method.getName();
                    if (methodName.startsWith(methodType)) {
                        // 如果值为null
                        Class returnType = nullFields.get(methodName.replace(methodType, ""));
                        if (Integer.class == returnType) {
                            method.invoke(t, 0);
                        } else if (String.class == returnType) {
                            if (methodName.endsWith("Status")) {
                                method.invoke(t, "0");
                            } else {
                                method.invoke(t, "");
                            }
                        } else if (Boolean.class == returnType) {
                            method.invoke(t, false);
                        } else if (Long.class == returnType) {
                            method.invoke(t, 0L);
                        } else if (Short.class == returnType) {
                            method.invoke(t, 0);
                        } else if (Double.class == returnType) {
                            method.invoke(t, 0.0D);
                        } else if (Date.class == returnType) {
                            method.invoke(t, DateUtil.nowDateTime());
                        }
                    }
                }
            } catch (InvocationTargetException e) {
                System.out.println("把实体类字段为空的设置默认值出错:");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println("把实体类字段为空的设置默认值出错:");
                e.printStackTrace();
            }
        }
        return t;
    }
}
