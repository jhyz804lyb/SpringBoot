package com.vuck.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

import static com.vuck.utils.Util.getParment;

public class ReflectionUtil
{

    /***
     * 获取私有成员变量的值
     *
     */
    public static Object getValue(Object instance, String fieldName)
        throws IllegalAccessException, NoSuchFieldException
    {

        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // 参数值为true，禁止访问控制检查
        return field.get(instance);
    }

    /***
     * 获取私有成员变量的值
     *
     */
    public static Object getValue(Object instance, Field field)
        throws IllegalAccessException
    {

        field.setAccessible(true); // 参数值为true，禁止访问控制检查

        return field.get(instance);
    }

    /***
     * 获取私有成员变量的值
     *
     */
    public static Object getValue(Object instance, Class tigerClass, String fieldName)
        throws IllegalAccessException, NoSuchFieldException
    {

        Field field = tigerClass.getDeclaredField(fieldName);
        field.setAccessible(true); // 参数值为true，禁止访问控制检查

        return field.get(instance);
    }

    /***
     * 设置私有成员变量的值
     *
     */
    public static void setValue(Object instance, String fileName, Object value)
        throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {

        Field field = instance.getClass().getDeclaredField(fileName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    /***
     * 访问私有方法
     *
     */
    public static Object callMethod(Object instance, String methodName, Class[] classes, Object[] objects)
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {

        Method method = instance.getClass().getDeclaredMethod(methodName, classes);
        method.setAccessible(true);
        return method.invoke(instance, objects);
    }

    /***
     * 访问私有方法
     *
     */
    public static Method getMethod(Class type, String methodName)
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {
        Method[] methods = type.getMethods();
        for (Method method : methods)
        {
            if (methodName.equals(method.getName())) return method;

        }
        return null;
    }

    public static Field getField(Class tigerClass, String fieldName) throws NoSuchFieldException
    {
        Field field = tigerClass.getDeclaredField(fieldName);
        return field;
    }

    /**
     * 初始化1 个bean 从另一个bean中复制值 这里要求字段名称和类型一致
     *
     * @param bean
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T initBeanValue(Class<T> bean, Object obj) throws Exception
    {
        T result = bean.newInstance();
        Field[] resultFields = bean.getDeclaredFields();
        Field[] beanField = obj.getClass().getDeclaredFields();
        for (Field r : resultFields)
        {
            for (Field b : beanField)
            {
                if (r.getType().equals(b.getType()) && r.getName().equals(b.getName()))
                {
                    r.setAccessible(true);
                    b.setAccessible(true);
                    r.set(result, b.get(obj));
                }
            }
        }
        return result;

    }

    public static <T> T initBeanValue(Class<T> bean, Object obj, Class objClass) throws Exception
    {
        T result = bean.newInstance();
        Field[] resultFields = bean.getDeclaredFields();
        Field[] beanField = objClass.getDeclaredFields();
        for (Field r : resultFields)
        {
            for (Field b : beanField)
            {
                if (r.getType().equals(b.getType()) && r.getName().equals(b.getName()))
                {
                    r.setAccessible(true);
                    b.setAccessible(true);
                    r.set(result, b.get(obj));
                }
            }
        }
        return result;

    }

    /**
     * 获取字段的get方法
     *
     * @param c
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getGetter(Class c, String fieldName) throws NoSuchMethodException
    {
        Method getter = null;
        if (Annotation.class.isAssignableFrom(c))
        {
            getter = c.getMethod(fieldName);
        }
        else
        {
            String methodName0 = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            String methodName = methodName0;
            if (fieldName.length() > 1 && Character.isUpperCase(fieldName.charAt(1)))
            {
                methodName = fieldName;
            }

            Object th = null;

            try
            {
                getter = c.getMethod("get" + methodName);
            }
            catch (NoSuchMethodException var13)
            {
                th = var13;
            }
            catch (NoSuchMethodError var14)
            {
                th = var14;
            }

            if (getter == null)
            {
                try
                {
                    getter = c.getMethod("is" + methodName);
                }
                catch (NoSuchMethodError var11)
                {
                    ;
                }
                catch (NoSuchMethodException var12)
                {
                    ;
                }
            }

            if (getter == null && !methodName0.equals(methodName))
            {
                try
                {
                    getter = c.getMethod("get" + methodName0);
                }
                catch (NoSuchMethodError var9)
                {
                    ;
                }
                catch (NoSuchMethodException var10)
                {
                    ;
                }

                if (getter == null)
                {
                    try
                    {
                        getter = c.getMethod("is" + methodName0);
                    }
                    catch (NoSuchMethodError var7)
                    {
                        ;
                    }
                    catch (NoSuchMethodException var8)
                    {
                        ;
                    }
                }
            }

            if (getter == null)
            {
                if (th instanceof NoSuchMethodException)
                {
                    throw (NoSuchMethodException) th;
                }

                if (th instanceof NoSuchMethodError)
                {
                    throw (NoSuchFieldError) th;
                }
            }
        }

        return getter;
    }

    /**
     * 通过get方法获取值
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getValueByMethod(Object obj, String fieldName) throws Exception
    {
        if (obj instanceof Map)
        {
            return ((Map) obj).get(fieldName);
        }
        else
        {
            Method getter = getGetter(obj.getClass(), fieldName);
            getter.setAccessible(true);
            return getter.invoke(obj);
        }
    }

    /**
     * 通过set方法设置值
     *
     * @param obj
     * @param property
     * @param value
     * @throws Exception
     */
    public static void setValueByMethod(Object obj, String property, Object value) throws Exception
    {
        setValue(obj, property, value, obj.getClass());
    }

    /**
     * 通过set方法设置值
     *
     * @param obj
     * @param property
     * @param value
     * @param type
     * @throws Exception
     */
    public static void setValue(Object obj, String property, Object value, Type type) throws Exception
    {
        Method method = setGetter(obj.getClass(), property);
        if (method != null && method.getParameterTypes()[1].isAssignableFrom(value.getClass()))
            method.invoke(obj, value);
    }

    /**
     * 获取set方法
     *
     * @param c
     * @param property
     * @return
     * @throws NoSuchMethodException
     */
    public static Method setGetter(Class c, String property) throws NoSuchMethodException
    {
        Method getter = null;
        if (Annotation.class.isAssignableFrom(c))
        {
            getter = c.getMethod(property);
        }
        else
        {
            String methodName0 = Character.toUpperCase(property.charAt(0)) + property.substring(1);
            String methodName = methodName0;
            if (property.length() > 1 && Character.isUpperCase(property.charAt(1)))
            {
                methodName = property;
            }

            Object th = null;

            try
            {
                getter = c.getMethod("set" + methodName);
            }
            catch (NoSuchMethodException var13)
            {
                th = var13;
            }
            catch (NoSuchMethodError var14)
            {
                th = var14;
            }

            if (getter == null)
            {
                try
                {
                    getter = c.getMethod("set" + methodName);
                }
                catch (NoSuchMethodError var11)
                {
                    ;
                }
                catch (NoSuchMethodException var12)
                {
                    ;
                }
            }

            if (getter == null && !methodName0.equals(methodName))
            {
                try
                {
                    getter = c.getMethod("set" + methodName0);
                }
                catch (NoSuchMethodError var9)
                {
                    ;
                }
                catch (NoSuchMethodException var10)
                {
                    ;
                }

                if (getter == null)
                {
                    try
                    {
                        getter = c.getMethod("set" + methodName0);
                    }
                    catch (NoSuchMethodError var7)
                    {
                        ;
                    }
                    catch (NoSuchMethodException var8)
                    {
                        ;
                    }
                }
            }

            if (getter == null)
            {
                if (th instanceof NoSuchMethodException)
                {
                    throw (NoSuchMethodException) th;
                }

                if (th instanceof NoSuchMethodError)
                {
                    throw (NoSuchFieldError) th;
                }
            }
        }

        return getter;
    }

    /**
     * 得到真实值，没有转换过得值
     *
     * @param bean
     * @param filedName
     * @param className
     * @return
     * @throws IllegalAccessException
     */
    public static Object getActualVal(Object bean, String filedName, Class className)
        throws IllegalAccessException
    {
        if (filedName.indexOf(".") != -1)
        {
            String fileds = filedName.substring(filedName.indexOf(".") + 1, filedName.length());
            String str = filedName.substring(0, filedName.indexOf("."));
            for (Field field : className.getDeclaredFields())
                if (field.getName().equals(str))
                {
                    field.setAccessible(true);
                    return getFiledVal(field.get(bean), fileds, field.getType());
                }
        }
        else
        {
            if (bean == null) return null;
            for (Field field : className.getDeclaredFields())
            {
                if (field.getName().equals(filedName))
                {
                    getValue(bean, field);
                    field.setAccessible(true);
                    Object obj = field.get(bean);
                    try
                    {
                        obj = obj == null ? getValueByMethod(bean, field.getName()) : obj;
                    }
                    catch (Exception e)
                    {
                    }
                    return obj;
                }
            }
        }

        return null;
    }

    /**
     * 通过字段得到封装后的值，时间会转成String yyyy-mm-dd.Double 会被金额格式化
     *
     * @param commerceInfo
     * @param filedName
     * @param className
     * @return
     * @throws IllegalAccessException
     */
    public static Object getFiledVal(Object commerceInfo, String filedName, Class className)
        throws IllegalAccessException
    {
        if (filedName.indexOf(".") != -1)
        {
            String fileds = filedName.substring(filedName.indexOf(".") + 1, filedName.length());
            String str = filedName.substring(0, filedName.indexOf("."));
            for (Field field : className.getDeclaredFields())
                if (field.getName().equals(str))
                {
                    field.setAccessible(true);
                    return getFiledVal(field.get(commerceInfo), fileds, field.getType());
                }
        }
        else
        {
            if (commerceInfo == null) return null;
            for (Field field : className.getDeclaredFields())
            {
                if (field.getName().equals(filedName))
                {
                    if (field.getType().getName().equals("java.util.Date"))
                    {
                        field.setAccessible(true);
                        Object obj = field.get(commerceInfo);
                        try
                        {
                            obj = obj == null ? getValueByMethod(commerceInfo, field.getName()) : obj;
                        }
                        catch (Exception e)
                        {
                        }
                        return obj == null ? "" : DateUtils.format((java.util.Date) obj, DateUtils.FORMAT_SHORT);
                    }
                    if (field.getType().getName().equals("java.sql.Date"))
                    {
                        field.setAccessible(true);
                        Object obj = field.get(commerceInfo);
                        try
                        {
                            obj = obj == null ? getValueByMethod(commerceInfo, field.getName()) : obj;
                        }
                        catch (Exception e)
                        {
                        }
                        return obj == null ? "" : DateUtils.format((java.sql.Date) obj, DateUtils.FORMAT_SHORT);
                    }

                    if ((field.getType().getName().equals("java.lang.Double")) ||
                        (field.getType().getSimpleName().equals("double")))
                    {
                        field.setAccessible(true);
                        Object obj = field.get(commerceInfo);
                        try
                        {
                            obj = obj == null ? getValueByMethod(commerceInfo, field.getName()) : obj;
                        }
                        catch (Exception e)
                        {
                        }
                        return NumberUtils.keepPrecision("" + obj, 2);
                    }
                    if ((field.getType().getName().equals("java.lang.Integer")) ||
                        (field.getType().getSimpleName().equals("int")))
                    {
                        field.setAccessible(true);
                        Object obj = field.get(commerceInfo);
                        try
                        {
                            obj = obj == null ? getValueByMethod(commerceInfo, field.getName()) : obj;
                        }
                        catch (Exception e)
                        {
                        }
                        return obj == null ? "" : String.valueOf(obj);
                    }
                    field.setAccessible(true);
                    Object obj = field.get(commerceInfo);
                    try
                    {
                        obj = obj == null ? getValueByMethod(commerceInfo, field.getName()) : obj;
                    }
                    catch (Exception e)
                    {
                    }
                    return obj;
                }
            }
        }

        return "";
    }

    /**
     * 得到真实的字段，一对一关系的实体中经常出现 bean.bean2.name 这个方法可以得到bean2的name字段
     *
     * @param filedName
     * @param otherClass
     * @return
     */

    public static Field getActualField(String filedName, Class otherClass)
    {
        if (filedName.indexOf(".") != -1)
        {
            String fileds = filedName.substring(filedName.indexOf(".") + 1, filedName.length());
            String temp = filedName.substring(0, filedName.indexOf("."));
            for (Field field : otherClass.getDeclaredFields())
            {
                if (field.getName().equals(temp))
                {
                    return getActualField(fileds, field.getType());
                }
            }
        }
        else
        {
            for (Field field : otherClass.getDeclaredFields())
            {
                if (field.getName().equals(filedName))
                {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 获取类的字段
     *
     * @param type
     * @return
     */
    public static Field[] getClassFiled(Class type)
    {
        return type.getDeclaredFields();
    }

    /**
     * 得到类上面的注解
     *
     * @param type
     * @param annotation
     * @param <T>
     * @return
     */
    public static <T> Annotation getAnnotation(Class type, T annotation)
    {
        for (Annotation annotation1 : type.getAnnotations())
        {
            if (annotation1.annotationType().equals(annotation))
                return annotation1;
        }
        return null;
    }


    /**
     * 得到字段上面的注解
     *
     * @param annotation
     * @param <T>
     * @return
     */
    public static <T> Annotation getFieldAnnotation(Field field, T annotation)
    {
        for (Annotation annotation1 : field.getAnnotations())
        {
            if (annotation1.annotationType().equals(annotation))
                return annotation1;
        }
        return null;
    }

    /**
     * 判断字段上是否拥有注解
     *
     * @param field
     * @param annotation
     * @param <T>
     * @return
     */
    public static <T> boolean hasFieldAnnotation(Field field, T annotation)
    {
        for (Annotation annotation1 : field.getAnnotations())
        {
            if (annotation1.annotationType().equals(annotation))
                return true;
        }
        return false;
    }

    /**
     * 判断类上面是否拥有某个注解
     *
     * @param type
     * @param annotation
     * @param <T>
     * @return
     */
    public static <T> boolean hasAnnotation(Class type, T annotation)
    {
        for (Annotation annotation1 : type.getAnnotations())
        {
            if (annotation1.annotationType().equals(annotation))
                return true;
        }
        return false;
    }

    /**
     * @param type
     * @return
     */
    public static Field[] getFieldArray(Class type, Class an)
    {
        int count = 0;
        Field[] temp = new Field[getAnnotationCount(type, an)];
        Field[] parment = getParment(type);
        for (Field field : parment)
        {
            Annotation annotation = getFieldAnnotation(field, an);
            if (annotation != null) temp[count++] = field;
        }
        return temp;
    }

    /**
     * @param type
     * @return
     */
    public static int getAnnotationCount(Class type, Class an)
    {
        int count = 0;
        Field[] parment = getParment(type);
        for (Field field : parment)
        {
            Annotation annotation = getFieldAnnotation(field, an);
            if (annotation != null) count++;
        }
        return count;
    }
}