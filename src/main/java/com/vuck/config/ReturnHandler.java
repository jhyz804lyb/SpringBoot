package com.vuck.config;

import com.vuck.annotations.ExportExcel;
import com.vuck.annotations.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuck.utils.Util;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回处理器
 *
 * @author liyabin
 * @date 2017-08-22下午 5:31
 */
@Component
public class ReturnHandler implements HandlerMethodReturnValueHandler
{
    public ReturnHandler()
    {
    }

    /**
     * 目前拓展JSON  注解和ExportExcel 实现自动的到出和Json转换
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter)
    {
        return (methodParameter.getMethod().getAnnotation(Json.class) != null) ||
                (methodParameter.getMethod().getAnnotation(ExportExcel.class) != null);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest)
            throws Exception
    {
        if (methodParameter.getMethod().getAnnotation(ExportExcel.class) != null)
        {
           Util.ExportExcelFile(returnValue,Util.getClassByMethodParameter(methodParameter),nativeWebRequest.getNativeResponse(HttpServletResponse.class),methodParameter.getMethod().getAnnotation(ExportExcel.class));
        }
        modelAndViewContainer.setRequestHandled(true);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(out,returnValue);
        }
        catch (IOException e)
        {
            throw e;
        }
    }
}
