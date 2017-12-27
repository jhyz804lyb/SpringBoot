package com.vuck.h_dao;

import com.vuck.common.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * hibernate 底层dao
 *
 * @author liyabin
 * @date 2017/12/26
 */
public interface HqlBaseDao<T> {

    public <T> List<T> query(Class<T> beanType, Map<String, String> params) throws Exception;

    public <T> List<T> query(Class<T> beanType, HttpServletRequest request) throws Exception;

    public <T> List<T> query(Class<T> beanType, Map<String, String> params, PageInfo pageInfo, Boolean isFindKey)
            throws Exception;

    public <T> List<T> query(Class<T> beanType, HttpServletRequest request, PageInfo pageInfo) throws Exception;

    public <T> List<T> query(Class<T> beanType, HttpServletRequest request, PageInfo pageInfo, Boolean isFindKey)
            throws Exception;

    public <T> List<T> query(String entityName, Class<T> beanType, HttpServletRequest request, PageInfo pageInfo, Boolean isFindKey)
            throws Exception;

    public <T> List<T> query(String entityName, Class<T> beanType, Map<String, String> params, PageInfo pageInfo, Boolean isFindKey)
            throws Exception;

    public <T> List<T> queryByHQL(String hql, Class<T> beanType, HttpServletRequest request, PageInfo pageInfo, Boolean isFindKey) throws Exception;

    public <T> List<T> queryByHQL(String hql, Class<T> beanType, Map<String, String> params, PageInfo pageInfo, Boolean isFindKey) throws Exception;

    public <T> T add(T beanType) throws Exception;

    public <T> T delete(T beanType) throws Exception;

    public <T> T edit(T beanType) throws Exception;

}