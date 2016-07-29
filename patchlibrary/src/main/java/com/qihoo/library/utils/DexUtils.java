package com.qihoo.library.utils;

import com.qihoo.library.annotation.UnInject;

import java.lang.reflect.Array;

import dalvik.system.DexClassLoader;

@UnInject
public class DexUtils {

    public static void injectClassLoader(String dexPath, String optimizedPath) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        ClassLoader currClassloader = getCurrentClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedPath, dexPath, currClassloader);
        ReflectionUtils.exchangeFields(currClassloader, dexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"));
        ReflectionUtils.exchangeFields(currClassloader, dexClassLoader, ClassLoader.class);
        ReflectionUtils.setField(currClassloader, ClassLoader.class, "parent", dexClassLoader);
    }

    public static void injectDexAtFirst(String dexPath, String optimizedPath) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedPath, dexPath, getCurrentClassLoader());
        Object baseDexElements = getDexElements(getPathList(getCurrentClassLoader()));
        Object newDexElements = getDexElements(getPathList(dexClassLoader));
        Object allDexElements = combineArray(newDexElements, baseDexElements);
        Object pathList = getPathList(getCurrentClassLoader());
        ReflectionUtils.setField(pathList, pathList.getClass(), "dexElements", allDexElements);
    }

    private static ClassLoader getCurrentClassLoader() {
        return  DexUtils.class.getClassLoader();
    }

    private static Object getDexElements(Object paramObject)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        return ReflectionUtils.getField(paramObject, paramObject.getClass(), "dexElements");
    }

    private static Object getPathList(Object baseDexClassLoader)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        return ReflectionUtils.getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object combineArray(Object firstArray, Object secondArray) {
        Class<?> localClass = firstArray.getClass().getComponentType();
        int firstArrayLength = Array.getLength(firstArray);
        int allLength = firstArrayLength + Array.getLength(secondArray);
        Object result = Array.newInstance(localClass, allLength);
        for (int k = 0; k < allLength; ++k) {
            if (k < firstArrayLength) {
                Array.set(result, k, Array.get(firstArray, k));
            } else {
                Array.set(result, k, Array.get(secondArray, k - firstArrayLength));
            }
        }
        return result;
    }

}
