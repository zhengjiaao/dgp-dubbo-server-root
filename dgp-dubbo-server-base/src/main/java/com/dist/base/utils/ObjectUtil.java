package com.dist.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

/**
 * object对象工具
 */
public abstract class ObjectUtil {


    /**
     * 传入的对象都不为null
     *
     * @param args
     * @return
     */
    public static boolean isNonNull(Object... args) {

        for (Object arg : args) {
            if (Objects.isNull(arg)) {
                return false;
            }
            if (arg instanceof String) {
                if (StringUtils.isBlank((String) arg)) {
                    return false;
                }
            }
            if (arg instanceof Collection) {
                if (((Collection) arg).size() == 0) {
                    return false;
                }
            }
            if (arg instanceof File) {
                if (!((File) arg).exists()) {
                    return false;
                }
            }

        }

        return true;
    }


    /**
     * 有一个为空，就返回true
     *
     * @param args
     * @return
     */
    public static boolean isAnyNull(Object... args) {
        for (Object arg : args) {
            if (!isNonNull(arg)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 将src对象中不为空的字段合并到target对象中
     */
    public static <T> void mergeObject(T src, T target) {


        Field[] declaredFields = src.getClass().getDeclaredFields();
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object srcFieldValue = declaredField.get(src);
                if (ObjectUtil.isNonNull(srcFieldValue)) {
                    declaredField.set(target, srcFieldValue);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
