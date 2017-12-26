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
public interface HqlBaseDao<T>
{
    public <T> List<T> query(Class<T> beanType, Map<String, String> params);

    public <T> List<T> query(Class<T> beanType, HttpServletRequest request);

    public <T> List<T> query(Class<T> beanType, Map<String, String> params, PageInfo pageInfo);

    public <T> List<T> query(Class<T> beanType, HttpServletRequest request, PageInfo pageInfo);

    public <T> T add(T beanType);

    public <T> T delete(T beanType);

    public <T> T edit(T beanType);

}