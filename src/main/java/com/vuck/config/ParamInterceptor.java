package com.vuck.config;

import com.vuck.common.Cost;
import com.vuck.h_dao.HqlBaseDao;
import com.vuck.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;

@Component
public class ParamInterceptor implements HandlerInterceptor
{
    @Autowired
    HqlBaseDao baseDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception
    {
        //如果不是方法级别的请求，结束当前拦截器
        if (!(o instanceof HandlerMethod)) return true;
        //得到当前请求的方法
        HandlerMethod method = (HandlerMethod) o;
        Object result = ConfigUtils.invokeMethod(method, httpServletRequest);
        httpServletRequest.setAttribute(Cost.FRAME_PARAM, result);
        httpServletRequest.setAttribute(Cost.REQUEST_URL,httpServletRequest.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception
    {

    }
}