package com.vuck.utils;
import com.vuck.annotations.*;
import com.vuck.exception.DataNoMatchException;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * HQL 语句构造器
 *
 * @author liyabin
 * @date 2017/12/21
 */
public class HqlConstructor
{
    public HqlConstructor()
    {
    }

    /**
     * 构建HQL语句，推荐使用有排序的Map LinkedHashMap
     *
     * @param beanType
     * @param values
     * @return
     */
    public static String createCountHQL(Class beanType, Map<String, String> values) throws Exception
    {
        if (beanType == null || values == null) return null;
        StringBuilder HQL = new StringBuilder();
        Iterator<String> iterator = values.keySet().iterator();
        Entity entity = (Entity) ReflectionUtil.getAnnotation(beanType, Entity.class);
        HQL.append("select count(1) from ").append(entity == null ? beanType.getSimpleName() : entity.name());
        boolean first = true;
        while (iterator.hasNext())
        {
            String key = iterator.next();
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class))
                continue; //如果这个字段不需要作为查询条件的话那么也跳过。
            if (first)
                HQL.append(" where ");
            else HQL.append(" and ");
            // Between 表示查时间区段 例如1月份到二月份
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            FindKey findKey = (FindKey) ReflectionUtil.getFieldAnnotation(actualField, FindKey.class);
            if (between != null)
            {
                HQL.append(key).append(">=").append(":").append(key).append("_").append(between.startField()).append(" and ");
                HQL.append(key).append("<=").append(":").append(key).append("_").append(between.endField());
            }
            else
            {
                HQL.append(key).append(ReflectionUtil.hasFieldAnnotation(actualField, FindKey.class) ?
                        ":" + findKey.selectType() : "=:").append(key);
            }
            first = false;
        }
        HQL.append(getOrderByStr("", beanType));
        return HQL.toString();
    }

    /**
     * 构建HQL语句，推荐使用有排序的Map LinkedHashMap
     *
     * @param beanType
     * @param values
     * @return
     */
    public static String createHQLByBean(Class beanType, Map<String, String> values) throws Exception
    {
        if (beanType == null || values == null) return null;
        StringBuilder HQL = new StringBuilder();
        Iterator<String> iterator = values.keySet().iterator();
        Entity entity = (Entity) ReflectionUtil.getAnnotation(beanType, Entity.class);
        HQL.append("select * from ").append(entity == null ? beanType.getSimpleName() : entity.name());
        boolean first = true;
        while (iterator.hasNext())
        {
            String key = iterator.next();
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class))
                continue; //如果这个字段不需要作为查询条件的话那么也跳过。
            if (first)
                HQL.append(" where ");
            else HQL.append(" and ");
            // Between 表示查时间区段 例如1月份到二月份
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            FindKey findKey = (FindKey) ReflectionUtil.getFieldAnnotation(actualField, FindKey.class);
            if (between != null)
            {
                HQL.append(key).append(">=").append(":").append(key).append("_").append(between.startField()).append(" and ");
                HQL.append(key).append("<=").append(":").append(key).append("_").append(between.endField());
            }
            else
            {
                HQL.append(key).append(ReflectionUtil.hasFieldAnnotation(actualField, FindKey.class) ?
                        ":" + findKey.selectType() : "=:").append(key);
            }
            first = false;
        }
        HQL.append(getOrderByStr("", beanType));
        return HQL.toString();
    }
    /**
     * 构建HQL语句
     *
     * @param beanType
     * @param values
     * @return
     */
    public static String createCountHQLByBean(Class beanType, List<String> values) throws Exception
    {
        if (beanType == null || values == null) return null;
        StringBuilder HQL = new StringBuilder();
        Entity entity = (Entity) ReflectionUtil.getAnnotation(beanType, Entity.class);
        HQL.append("select count(1) from ").append(entity == null ? beanType.getSimpleName() : entity.name());
        boolean first = true;
        for (String key : values)
        {
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class))
                continue; //如果这个字段不需要作为查询条件的话那么也跳过。
            if (first)
                HQL.append(" where ");
            else HQL.append(" and ");
            // Between 表示查时间区段 例如1月份到二月份
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            FindKey findKey = (FindKey) ReflectionUtil.getFieldAnnotation(actualField, FindKey.class);
            if (between != null)
            {
                HQL.append(key).append(">=").append(":").append(between.startField()).append(" and ");
                HQL.append(key).append("<=").append(":").append(between.endField());
            }
            else
            {
                HQL.append(key).append(ReflectionUtil.hasFieldAnnotation(actualField, FindKey.class) ?
                        ":" + findKey.selectType() : "=:").append(key);
            }
            first = false;
        }
        HQL.append(getOrderByStr("", beanType));
        return HQL.toString();
    }

    /**
     * 构建HQL语句
     *
     * @param beanType
     * @param values
     * @return
     */
    public static String createHQLByBean(Class beanType, List<String> values) throws Exception
    {
        if (beanType == null || values == null) return null;
        StringBuilder HQL = new StringBuilder();
        Entity entity = (Entity) ReflectionUtil.getAnnotation(beanType, Entity.class);
        HQL.append("select * from ").append(entity == null ? beanType.getSimpleName() : entity.name());
        boolean first = true;
        for (String key : values)
        {
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class))
                continue; //如果这个字段不需要作为查询条件的话那么也跳过。
            if (first)
                HQL.append(" where ");
            else HQL.append(" and ");
            // Between 表示查时间区段 例如1月份到二月份
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            FindKey findKey = (FindKey) ReflectionUtil.getFieldAnnotation(actualField, FindKey.class);
            if (between != null)
            {
                HQL.append(key).append(">=").append(":").append(between.startField()).append(" and ");
                HQL.append(key).append("<=").append(":").append(between.endField());
            }
            else
            {
                HQL.append(key).append(ReflectionUtil.hasFieldAnnotation(actualField, FindKey.class) ?
                        ":" + findKey.selectType() : "=:").append(key);
            }
            first = false;
        }
        HQL.append(getOrderByStr("", beanType));
        return HQL.toString();
    }

    /**
     * 构建HQL语句
     *
     * @param beanType
     * @return
     */
    public static String createHQLByBean(Class beanType) throws Exception
    {
        if (beanType == null) return null;
        StringBuilder HQL = new StringBuilder();
        Entity entity = (Entity) ReflectionUtil.getAnnotation(beanType, Entity.class);
        HQL.append("select * from ").append(entity == null ? beanType.getSimpleName() : entity.name());
        boolean first = true;
        for (Field actualField : ReflectionUtil.getClassFiled(beanType))
        {
            String key = actualField.getName();
            if (ReflectionUtil.hasFieldAnnotation(actualField, Id.class)) continue;
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class))
                continue; //如果这个字段不需要作为查询条件的话那么也跳过。
            if (first)
                HQL.append(" where ");
            else HQL.append(" and ");
            // Between 表示查时间区段 例如1月份到二月份
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            FindKey findKey = (FindKey) ReflectionUtil.getFieldAnnotation(actualField, FindKey.class);
            if (between != null)
            {
                HQL.append(key).append(">=").append(":").append(between.startField()).append(" and ");
                HQL.append(key).append("<=").append(":").append(between.endField());
            }
            else
            {
                HQL.append(key).append(ReflectionUtil.hasFieldAnnotation(actualField, FindKey.class) ?
                        ":" + findKey.selectType() : "=:").append(key);
            }
            first = false;
        }
        HQL.append(getOrderByStr("", beanType));
        return HQL.toString();
    }


    /**
     * 得到排序字段
     *
     * @param asName
     * @param entityClass
     * @return
     * @throws Exception
     */
    public static String getOrderByStr(String asName, Class entityClass) throws Exception
    {
        String tempAs = asName == null ? "" : asName;
        if (entityClass == null) throw new DataNoMatchException();
        Field[] declaredFields = entityClass.getDeclaredFields();
        List<Field> orderList = new ArrayList<Field>(declaredFields.length);
        for (Field field : declaredFields)
        {
            if (ReflectionUtil.hasFieldAnnotation(field, OrderByField.class)) orderList.add(field);
        }
        orderList = sortOrderFiled(orderList);
        StringBuilder orderStr = new StringBuilder();
        String orderBy = (orderList != null && orderList.size() > 0) ? "  order by " : "";
        orderStr.append(orderBy);
        for (Field filed : orderList)
        {
            OrderByField annotation = (OrderByField) ReflectionUtil.getFieldAnnotation(filed, OrderByField.class);
            orderStr.append(filed.getName() + " ").append(annotation.orderType()).append(",");

        }
        if (orderStr.length() > 0)
        {
            return orderStr.substring(0, orderStr.length() - 1);
        }
        return orderStr.toString();
    }

    /**
     * 排序
     *
     * @param list
     * @return
     */
    private static List<Field> sortOrderFiled(List<Field> list)
    {
        int size = list.size();
        for (int i = 0; i < size - 1; i++)
        {
            for (int j = 0; j < size - i - 1; j++)
            {
                Field filed1 = list.get(j);
                Field filed2 = list.get(j + 1);
                OrderByField order1 = (OrderByField) ReflectionUtil.getFieldAnnotation(filed1, OrderByField.class);
                OrderByField order2 = (OrderByField) ReflectionUtil.getFieldAnnotation(filed2, OrderByField.class);
                if (order1.orderId() > order2.orderId())
                {
                    Field temp = filed2;
                    list.set(j + 1, filed1);
                    list.set(j, temp);
                }
            }
        }
        return list;
    }

    public static <T> Map<String, String> getRequestParam(Map<String, String[]> params, Class<T> beanType)
    {
        if (params == null || beanType == null) return null;
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            String[] values = params.get(key);
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null || values == null || values.length == 0) continue;
            for (String value : values)
            {
                if (!StringUtils.isEmpty(value)) result.put(key, value);
            }
        }
        return result;
    }

}
