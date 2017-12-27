package com.vuck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.*;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration   //标注此文件为一个配置项，spring boot才会扫描到该配置。该注解类似于之前使用xml进行配置
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(ApplicationContextUtil.getApplicationContext().getBean(ParamInterceptor.class))
                .addPathPatterns("/*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        argumentResolvers.add(ApplicationContextUtil.getApplicationContext().getBean(ParamResolver.class));
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers)
    {
        returnValueHandlers.add(ApplicationContextUtil.getApplicationContext().getBean(ReturnHandler.class));
    }
}