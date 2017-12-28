package com.vuck.h_dao;

import com.vuck.annotations.*;
import com.vuck.common.PageInfo;
import com.vuck.utils.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 基层hibernate 实现类
 *
 * @author liyabin
 * @date 2017/12/26
 */
@Service
@Transactional
public class HqlBaseDaoImlp<T> implements HqlBaseDao<T>
{
    @Autowired
    protected SessionFactory sessionFactory;

    private Session session;

    public HqlBaseDaoImlp()
    {
    }

    public Session getSession()
    {
        try
        {
            if (session == null) session = sessionFactory.getCurrentSession();
        }
        catch (HibernateException e)
        {

        }
        if (session == null) session = sessionFactory.openSession();
        if (session == null) new Exception("this session has close!");
        return session;
    }

    @Override
    public <T> List<T> query(Class<T> beanType, Map<String, String> params) throws Exception
    {
        return query(beanType, params, null, null);
    }

    @Override
    public <T> List<T> query(Class<T> beanType, HttpServletRequest request) throws Exception
    {
        Map<String, String> requestParam = HqlConstructor.getRequestParam(request.getParameterMap(), beanType);
        return query(beanType, requestParam, null, null);
    }

    @Override
    public <T> List<T> query(Class<T> beanType, Map<String, String> params, PageInfo pageInfo, Boolean isFindKey)
            throws Exception
    {
        if (pageInfo == null)
        {
            String hql = HqlConstructor.createHQLByBean(null, beanType, params, isFindKey);
            Query query = createQuery(hql, params, beanType, isFindKey);
            return query.list();
        }
        else
        {
            String hql = HqlConstructor.createHQLByBean(null, beanType, params, isFindKey);
            Query query = createQuery(hql, params, beanType, isFindKey);
            String countHQL = HqlConstructor.createCountHQL(null, beanType, params, isFindKey);
            Integer count = getCount(countHQL, params, beanType, isFindKey);
            pageInfo.setMaxCount(count);
            pageInfo.setMaxPage(count % pageInfo.getPageCount() == 0 ? count / pageInfo.getPageCount() :
                    pageInfo.getPageCount() + 1);
            query.setFirstResult((pageInfo.getPageNo()-1) * pageInfo.getPageCount());
            query.setMaxResults((pageInfo.getPageNo() + 1) * pageInfo.getPageCount());
            return query.list();
        }
    }

    @Override
    public <T> List<T> query(Class<T> beanType, HttpServletRequest request, PageInfo pageInfo) throws Exception
    {
        return query(beanType, request, pageInfo, null);
    }

    @Override
    public <T> List<T> query(Class<T> beanType, HttpServletRequest request, PageInfo pageInfo, Boolean isFindKey)
            throws Exception
    {
        Map<String, String> requestParam = HqlConstructor.getRequestParam(request.getParameterMap(), beanType);
        if (pageInfo != null)
        {
            pageInfo.setParameterUrl(Util.getRequestUrlParam(request));
            pageInfo.setUrl(request.getRequestURI());
        }
        return query(beanType, requestParam, pageInfo, isFindKey);
    }

    @Override
    public <T> List<T> query(String entityName, Class<T> beanType, HttpServletRequest request, PageInfo pageInfo,
                             Boolean isFindKey) throws Exception
    {
        Map<String, String> requestParam = HqlConstructor.getRequestParam(request.getParameterMap(), beanType);
        if (pageInfo != null)
        {
            pageInfo.setParameterUrl(Util.getRequestUrlParam(request));
            pageInfo.setUrl(request.getRequestURI());
        }
        return query(entityName, beanType,requestParam,pageInfo,isFindKey);
    }

    @Override
    public <T> List<T> query(String entityName, Class<T> beanType, Map<String, String> params, PageInfo pageInfo,
                             Boolean isFindKey)
            throws Exception
    {
        if (pageInfo == null)
        {
            String hql = HqlConstructor.createHQLByBean(entityName, beanType, params, isFindKey);
            Query query = createQuery(hql, params, beanType, isFindKey);
            return query.list();
        }
        else
        {
            String hql = HqlConstructor.createHQLByBean(entityName, beanType, params, isFindKey);
            Query query = createQuery(hql, params, beanType, isFindKey);
            String countHQL = HqlConstructor.createCountHQL(entityName, beanType, params, isFindKey);
            Integer count = getCount(countHQL, params, beanType, isFindKey);
            pageInfo.setMaxCount(count);
            pageInfo.setMaxPage(count % pageInfo.getPageCount() == 0 ? count / pageInfo.getPageCount() :
                count/pageInfo.getPageCount() + 1);
            query.setFirstResult((pageInfo.getPageNo()-1) * pageInfo.getPageCount());
            query.setMaxResults(pageInfo.getPageCount());
            return query.list();
        }
    }

    @Override
    public <T> List<T> queryByHQL(String hql, Class<T> beanType, HttpServletRequest request, PageInfo pageInfo, Boolean isFindKey) throws Exception {
        Map<String, String> requestParam = HqlConstructor.getRequestParam(request.getParameterMap(), beanType);
        if (pageInfo != null)
        {
            pageInfo.setParameterUrl(Util.getRequestUrlParam(request));
            pageInfo.setUrl(request.getRequestURI());
        }
        return queryByHQL(hql,beanType,requestParam,pageInfo,isFindKey);
    }

    @Override
    public <T1> List<T1> queryByHQL(String hql, Class<T1> beanType, Map<String, String> params, PageInfo pageInfo, Boolean isFindKey) throws Exception {

        if (pageInfo == null)
        {
            Query query = createQuery(hql, params, beanType, isFindKey);
            return query.list();
        }
        else
        {
            Query query = createQuery(hql, params, beanType, isFindKey);
            String countHQL = HqlConstructor.createCountHQL(hql);
            Integer count = getCount(countHQL, params, beanType, isFindKey);
            pageInfo.setMaxCount(count);
            pageInfo.setMaxPage(count % pageInfo.getPageCount() == 0 ? count / pageInfo.getPageCount() :
                count/pageInfo.getPageCount() + 1);
            query.setFirstResult((pageInfo.getPageNo()-1) * pageInfo.getPageCount());
            query.setMaxResults(pageInfo.getPageCount());
            return query.list();
        }
    }

    @Override
    public <T> T add(T beanType)
    {
        getSession().saveOrUpdate(beanType);
        return beanType;
    }

    @Override
    public <T> T delete(T beanType)
    {
        getSession().delete(beanType);
        return beanType;
    }

    @Override
    public <T> T edit(T beanType)
    {
        getSession().saveOrUpdate(beanType);
        return beanType;
    }

    public <T> Query createQuery(String hql, Map<String, String> params, Class<T> beanType, Boolean isFindKey)
            throws Exception
    {
        Query query = getSession().createQuery(hql);
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            Field actualField = ReflectionUtil.getActualField(key, beanType);
            if (actualField == null) continue;//如果这个查询条件没有找到字段那么跳过。
            if (isFindKey != null && isFindKey && !ReflectionUtil.hasFieldAnnotation(actualField, FindKey.class))
                continue;
            if (ReflectionUtil.hasFieldAnnotation(actualField, NotSerachField.class)) continue;
            //区间查询
            Between between = (Between) ReflectionUtil.getFieldAnnotation(actualField, Between.class);
            if (between != null)
            {
                query.setParameter(key + "_" + between.startField(),
                        Util.parsueData(actualField, params.get(key + "_" + between.startField())));
                query.setParameter(key + "_" + between.endField(),
                        Util.parsueData(actualField, params.get(key + "_" + between.endField())));
            }
            else
                query.setParameter(key, Util.parsueData(actualField, params.get(key)));
        }
        return query;
    }

    /**
     * 获取分页总数
     *
     * @param countHQL
     * @param params
     * @param beanType
     * @return
     * @throws Exception
     */
    public <T> Integer getCount(String countHQL, Map<String, String> params, Class<T> beanType, Boolean isFindKey)
            throws Exception
    {
        Query query = createQuery(countHQL, params, beanType, isFindKey);
        Object countObject = query.uniqueResult();
        return Integer.parseInt(countObject.toString());
    }
}
