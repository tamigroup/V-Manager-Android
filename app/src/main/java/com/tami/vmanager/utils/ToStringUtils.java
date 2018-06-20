package com.tami.vmanager.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class ToStringUtils {

    public static final String GET = "get";

    /**
     * 返回JavaBena的toString方法值
     *
     * @param t
     * @return String
     */
    public static <T> String getToString(T t) {
        String result = null;
        Field[] fields = t.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName(); // 获取属性的名字
            Object value = getAttributeValue(t, name);// 获取属性值
            map.put(name, value != null ? value : null);
            name = null;
            value = null;
        }
        result = map.toString();
        fields = null;
        map.clear();
        map = null;
        return result;
    }

    /**
     * 返回JavaBena的toString方法值（只有属性的情况下）
     *
     * @param t
     * @return String
     */
    public static <T> String getToString1(T t) {
        String result = null;
        Field[] fields = t.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(t));
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        result = map.toString();
        fields = null;
        map.clear();
        map = null;
        return result;
    }

    /**
     * 通过反射，用属性名称获得属性值
     *
     * @param t
     * @param attributeName
     * @return Object
     */
    private static <T> Object getAttributeValue(T t, String attributeName) {
        Object result = null;
        Method method = null;
        try {
            StringBuffer methodName = new StringBuffer(GET);
            methodName.append(attributeName.substring(0, 1).toUpperCase());
            methodName.append(attributeName.substring(1));
            method = t.getClass().getMethod(methodName.toString());
            methodName = null;
            result = method.invoke(t);
            method = null;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}