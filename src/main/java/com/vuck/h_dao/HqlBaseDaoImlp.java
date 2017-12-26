package com.vuck.h_dao;

import com.vuck.annotations.Between;
import com.vuck.annotations.FindKey;
import com.vuck.annotations.NotSerachField;
import com.vuck.common.PageInfo;
import com.vuck.utils.HqlConstructor;
import com.vuck.utils.ReflectionUtil;
import com.vuck.utils.Util;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.QuerydslWebConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

/**
 * @author liyabin
 * @date 2017/12/26
 */
public class HqlBaseDaoImlp<T> implements HqlBaseDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    private Session session;

    public HqlBaseDaoImlp() {
    }

    public Session getSession() {
        if (session == null) session = sessionFactory.getCurrentSession();
        if (session == null) session = sessionFactory.openSession();
        if (session == null) new Exception("this session has close!");
        return session;
    }


    @Override
    public <T> List<T> query(Class<T> beanType, Map<String, String> params) {
        return query(beanType, params, null);
    }

    @Override
    public <T> List<T> query(Class<T> beanType, HttpServletRequest request) {
        Map<String, String> requestParam = HqlConstructor.getRequestParam(request.getParameterMap(), beanType);
        return query(beanType, requestParam, null);
    }

    @Override
    public <T> List<T> query(Class<T> beanType, Map<String, String> params, PageInfo pageInfo) {
        getSession().createQuery("");
        return null;
    }

    @Override
    public <T> List<T> query(Class<T> beanType, HttpServletRequest request, PageInfo pageInfo) {
        Map<String, String> requestParam = HqlConstructor.getRequestParam(request.getParameterMap(), beanType);
        return query(beanType, requestParam, pageInfo);
    }

    @Override
    public <T> T add(T beanType) {
        getSession().saveOrUpdate(beanType);
        return beanType;
    }

    @Override
    public <T> T delete(T beanType) {
        getSession().delete(beanType);
        return beanType;
    }

    @Override
    public <T> T edit(T beanType) {
        getSession().saveOrUpdate(beanType);
        return beanType;
    }

    public <T> Query createQuery(String hql, Map<String, String> params, Class<T> beanType) throws Exception {
        Query query = getSession().createQuery(hql);
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class)) continue;
            //区间查询
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            if (between != null) {//between 与 findKey 不会同时出现
                query.setParameter(key + "_" + between.startField(), Util.parsueData(actualField, params.get(key + "_" + between.startField())));
                query.setParameter(key + "_" + between.endField(), Util.parsueData(actualField, params.get(key + "_" + between.endField())));
            } else
                query.setParameter(key, Util.parsueData(actualField, params.get(key)));
        }
        return query;
    }
}
