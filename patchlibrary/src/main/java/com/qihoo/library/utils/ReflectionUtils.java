package com.qihoo.library.utils;

import android.util.Log;

import com.qihoo.library.annotation.UnInject;

import java.lang.reflect.Field;

@UnInject
public class ReflectionUtils {

    public static void exchangeFields(Object obj1, Object obj2, Class<?> cl)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field[] fields = cl.getDeclaredFields();
        for(Field field:fields){
            Log.e("ReflectionUtilse","xchangeFields "+field.getName());
            field.setAccessible(true);
            Object value1 = field.get(obj1);
            Object value2 = field.get(obj2);
            field.set(obj1, value2);
            field.set(obj2, value1);
        }
    }

    public static Object getField(Object obj, Class<?> cl, String field)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    public static void setField(Object obj, Class<?> cl, String field, Object value)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        localField.set(obj, value);
    }
}
