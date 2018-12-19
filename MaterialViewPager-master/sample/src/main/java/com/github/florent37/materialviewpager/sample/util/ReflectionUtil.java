package com.github.florent37.materialviewpager.sample.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

    /**
     * get declared field in the class and its ancestors
     *
     * @param type
     * @return
     */
    public static Field[] getFields(Class type) {
        List<Field> fieldsList = new ArrayList<Field>();

        for (Field field : type.getDeclaredFields()) {
            if (!Modifier.isAbstract(field.getModifiers())) {
                fieldsList.add(field);
            }
        }

        Class superType = type.getSuperclass();
        if (superType != null) {
            Field[] superFields = getFields(superType);

            for (Field field : superFields) {
                fieldsList.add(field);
            }
        }

        Field[] fields = new Field[fieldsList.size()];
        fieldsList.toArray(fields);
        return fields;
    }

    public static Field getField(Class type, String name) {
        Field field = null;

        try {
            field = type.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            if (type.getSuperclass() != null) {
                field = getField(type.getSuperclass(), name);
            } else {
                Log.e("Reflection Error", e.toString());
            }
        }

        return field;
    }

    public static Method getMethod(Class objType, String name, java.lang.Class<?>... parameterTypes) {
        Method method = null;

        try {
            method = objType.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            if (objType.getSuperclass() != null) {
                method = getMethod(objType.getSuperclass(), name, parameterTypes);
            } else {
                Log.e("Reflection Error", e.toString());
            }
        }

        return method;
    }

    public static boolean isAnnotationPresent(Field field, Class annotationClazz) {
        return field.isAnnotationPresent(annotationClazz);
    }

    public static boolean isAnnotationPresent(Class type, Method method, Class annotationType) {
        boolean isPresent = method.isAnnotationPresent(annotationType);
        Class superClass = type.getSuperclass();
        Method superMethod = superClass != null ? getMethod(superClass, method.getName(), method.getParameterTypes()) : null;
        if (!isPresent && (superMethod != null)) {
            isPresent = isAnnotationPresent(superClass, superMethod, annotationType);
        }

        return isPresent;
    }

    public static void setAccessible(Field field, boolean isAccessible) {
        if (field.isAccessible() != isAccessible) {
            field.setAccessible(isAccessible);
        }
    }

    public static void setAccessible(Method method, boolean isAccessible) {
        if (method.isAccessible() != isAccessible) {
            method.setAccessible(isAccessible);
        }
    }

    public static Type[] getParameterizedType(Field field, Class objClazz) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) genericType).getActualTypeArguments();
            if (isConcreteTypes(types)) {
                return types;
            }
        }

        Type genericSuperclass = objClazz.getGenericSuperclass();
        if (genericSuperclass != null) {
            if (genericSuperclass instanceof ParameterizedType) {
                return ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            } else {
                return getParameterizedType(objClazz.getSuperclass());
            }
        }

        Type[] types = objClazz.getGenericInterfaces();
        if (types != null && types.length > 0) {
            return types;
        }

        return new Type[0];
    }

    private static Type[] getParameterizedType(Class objClazz) {
        Class superClass = objClazz.getSuperclass();
        Type genericSuperclass = objClazz.getGenericSuperclass();
        while (genericSuperclass != null) {
            if (genericSuperclass instanceof ParameterizedType) {
                return ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            } else {
                genericSuperclass = superClass.getGenericSuperclass();
                superClass = superClass.getSuperclass();
            }
        }
        return new Type[0];
    }

    private static boolean isConcreteTypes(Type[] types) {
        for (Type type : types) {
            if (!(type instanceof Class)) {
                return false;
            }
        }

        return true;
    }

    public static Object getFieldValue(Object obj, Field field) throws java.lang.IllegalAccessException {
        if (null != obj)
            return field.get(obj);
        return null;
    }
}
