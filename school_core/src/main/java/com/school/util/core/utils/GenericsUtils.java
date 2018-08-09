package com.school.util.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Provides a helper that locates the declarated generics type of a class.
 *
 * @author sshwsfc@gmail.com
 */
@SuppressWarnings("unchecked")
public class GenericsUtils {

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {

            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }
}
