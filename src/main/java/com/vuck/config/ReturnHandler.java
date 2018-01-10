package com.vuck.config;

import com.vuck.annotations.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuck.utils.*;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

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
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter)
    {
        return (methodParameter.getMethod().getAnnotation(Json.class) != null) ||
                (methodParameter.getMethod().getAnnotation(ExportExcel.class) != null ||
                        methodParameter.getMethod().getAnnotation(ImageInfo.class) != null);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest)
            throws Exception
    {
        modelAndViewContainer.setRequestHandled(true);
        if (methodParameter.getMethod().getAnnotation(ExportExcel.class) != null)
        {
            Util.ExportExcelFile(returnValue, Util.getClassByMethodParameter(methodParameter),
                    nativeWebRequest.getNativeResponse(HttpServletResponse.class),
                    methodParameter.getMethod().getAnnotation(ExportExcel.class));
        }
        else if (methodParameter.getMethod().getAnnotation(ImageInfo.class) != null)
        {
            HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        /*    response.addHeader("Content-Disposition", String.format("attachment;filename*=utf-8'zh_cn'%s.png",
                    new Object[]{URLEncoder.encode("image", "utf-8")}));*/
            response.setHeader("Content-Disposition", "attachment;fileName=image.png");
            response.setContentType("image/png");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            InputStream inputStream = new FileInputStream("D:/temp/temp.png");
            //激活下载操作
            OutputStream os = response.getOutputStream();
            OutputStream stream = response.getOutputStream();
            stream.write((byte[])returnValue);
            response.flushBuffer();
            stream.flush();
            stream.close();
        }
        else
        {
            HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
            response.setContentType("text/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter())
            {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(out, returnValue);
            }
            catch (IOException e)
            {
                throw e;
            }
        }
    }
}
