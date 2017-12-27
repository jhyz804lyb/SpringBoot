package com.vuck.utils;

import com.vuck.annotations.*;
import com.vuck.common.*;
import com.vuck.config.*;
import com.vuck.h_dao.HqlBaseDao;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liyabin
 * @date 2017/12/27
 */
public class ConfigUtils {
    public static Object invokeMethod(HandlerMethod method, HttpServletRequest request) throws Exception {
        //控制层请求方法中的参数
        MethodParameter[] methodParameters = method.getMethodParameters();
        Object result = null;
        for (MethodParameter parameter : methodParameters) {
            //
            for (Annotation annotation : parameter.getParameterAnnotations())//可能一个参数存在多个注解
            {
                if (annotation instanceof Add) {

                }
                if (annotation instanceof SaveOrUpdate) {

                }
                if (annotation instanceof Find) {
                    //去数据库匹配数据
                    Find find = (Find) annotation;
                    result = findMethod(parameter, request, find);
                    request.setAttribute(Cost.BEAN_TYPE, Util.getClassByMethodParameter(parameter));
                    if (!find.entityClass().equals(Config.defaultClass) && result != null) {
                        Class classType = Util.getClassByMethodParameter(parameter);
                        //参数是List集合还是单个实体
                        boolean isList = Collection.class.isAssignableFrom(parameter.getParameterType());
                        if (isList) {
                            List results = new ArrayList();
                            for (Object obj : (List) result) {
                                results.add(ReflectionUtil.initBeanValue(classType, obj, find.entityClass()));
                            }
                            result = results;
                        } else {

                            result = ReflectionUtil.initBeanValue(classType, result, find.entityClass());
                        }
                    }
                }
                if (annotation instanceof Delete) {

                }
            }
        }
        return result;
    }

    public static Object findMethod(MethodParameter parameter, HttpServletRequest request, Find find)
            throws Exception {
        //通过spring容器获得 HqlBaseDao 的代理对象
        HqlBaseDao baseDao = ApplicationContextUtil.getApplicationContext().getBean(HqlBaseDao.class);
        Class classType = Util.getClassByMethodParameter(parameter);
        boolean isPage = Util.isPageList(parameter);//需要分页
        boolean isList = Collection.class.isAssignableFrom(parameter.getParameterType());//参数是List集合还是单个实体
        //如果是使用 Mybatis查询复杂的数据。 待完善......（未明确Mybatis 参数定义格式所以这个方法还不知道如何传递参数，待后续学习后完善）
        if (!find.invokeClass().equals(Config.defaultClass) && !StringUtils.isEmpty(find.invokeMethod())) {
            Object bean = ApplicationContextUtil.getApplicationContext().getBean(find.invokeClass());
            Method method = find.invokeClass().getMethod(find.invokeMethod());
            Object invoke = method.invoke(bean);
        } else if (!StringUtils.isEmpty(find.OQL()))//如果是用HQL自带的查询
        {
            if (isPage) {
                Annotation annotation =
                        Util.getAnnotation(parameter.getMethod(), parameter.getMethod().getDeclaringClass());
                Page temp = annotation instanceof Page ? (Page) annotation : null;
                PageInfo page = new PageInfo();
                page.setPageCount(temp == null ? 10 : temp.defaultCount());
                PageInfo pageInfo = Util.initPageInfo(request, page);
                List query = baseDao.queryByHQL(find.OQL(), classType, request, page, find.isSerachKey());
                //把分页信息设置到 request 内置对象中 便于前台分页获取
                request.setAttribute(Cost.PAGE_ATTR_NAME, pageInfo);
                if (isList) return query;
                else return ((query == null || query.size() == 0) ? null : query.get(0));
            } else {
                List query =
                        baseDao.queryByHQL(find.OQL(), classType, request, null, find.isSerachKey());
                if (isList) return query;
                else return ((query == null || query.size() == 0) ? null : query.get(0));
            }
        } else {
            //如果是把查询结果封装到DTO中 ，那么Find 注解中 entityClass 必须标实体类。
            Entity entity = (Entity) ReflectionUtil.getAnnotation(find.entityClass(), Entity.class);
            String entityName = null;
            if (!Config.defaultClass.equals(find.entityClass())) {
                entityName = entity == null ? find.entityClass().getSimpleName() : entity.name();
            }
            if (isPage) {
                // 初始化分页信息
                Annotation annotation =
                        Util.getAnnotation(parameter.getMethod(), parameter.getMethod().getDeclaringClass());
                Page temp = annotation instanceof Page ? (Page) annotation : null;
                PageInfo page = new PageInfo();
                page.setPageCount(temp == null ? 10 : temp.defaultCount());
                PageInfo pageInfo = Util.initPageInfo(request, page);
                List query = baseDao.query(entityName, classType, request, page, find.isSerachKey());
                //把分页信息设置到 request 内置对象中 便于前台分页获取
                request.setAttribute(Cost.PAGE_ATTR_NAME, pageInfo);
                if (isList) return query;
                else return ((query == null || query.size() == 0) ? null : query.get(0));
            } else {
                List query =
                        baseDao.query(entityName, classType, request, null, find.isSerachKey());
                if (isList) return query;
                else return ((query == null || query.size() == 0) ? null : query.get(0));
            }
        }
        return null;
    }

}
