package com.vuck.tag;

import com.vuck.annotations.Between;
import com.vuck.annotations.FindKey;
import com.vuck.common.Cost;
import com.vuck.utils.ReflectionUtil;
import com.vuck.utils.SortInteface;
import com.vuck.utils.StringUtils;
import com.vuck.utils.Util;

import javax.el.ELResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Map;

public class FromTag extends SimpleTagSupport
{

    private Class beanType;

    /**
     * 前端入参代表要显示的列
     */
    private String fields;
    /**
     * 列名
     */
    private String titleNames;

    private String[] titleList;

    private String[] fieldList;

    private String fontChar;

    private Field[] conditionFields;

    private String requestType;

    public void setBeanType(Class beanType)
    {
        this.beanType = beanType;
    }

    public void setFields(String fields)
    {
        this.fields = fields;
    }

    public void setTitleNames(String titleNames)
    {
        this.titleNames = titleNames;
    }

    public void setTitleList(String[] titleList)
    {
        this.titleList = titleList;
    }

    public void setFieldList(String[] fieldList)
    {
        this.fieldList = fieldList;
    }

    public void setFontChar(String fontChar)
    {
        this.fontChar = fontChar;
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }

    @Override
    public void doTag() throws JspException, IOException
    {
        if (beanType == null)
        {
            beanType = (Class) this.getJspContext().findAttribute(Cost.BEAN_TYPE);
        }
        if (beanType == null) return;
        init();
        if (fieldList == null || fieldList.length == 0) return;
        createHtml();
    }

    private void createHtml() throws IOException
    {
        this.getJspContext().findAttribute("matchName");
        ELResolver elResolver = this.getJspContext().getELContext().getELResolver();
        StringBuilder html = new StringBuilder();
        Object attribute = this.getJspContext().findAttribute(Cost.REQUEST_URL);
        String submitUrl = String.valueOf(attribute == null ? "" : attribute);
        if (StringUtils.isEmpty(submitUrl))
            submitUrl = ((HttpServletRequest) ((PageContext) this.getJspContext()).getRequest()).getRequestURI();
        html.append("<form id='form1' method='")
                .append("get".toUpperCase().equals(requestType.toUpperCase()) ? "get" : "post").append("'");
        if (!StringUtils.isEmpty(submitUrl))
        {
            html.append("acttion='").append(submitUrl).append("'").append(">");
            html.append("<script>").append("var fromUrl = '" + submitUrl + "';").append("var requestType =")
                    .append("get".toUpperCase().equals(requestType.toUpperCase()) ? "'get'" : "'post'").append(";")
                    .append("</script>");
        }
        else
            html.append(">");
        for (int i = 0; i < fieldList.length; i++)
        {
            if ((i + 1) % 2 != 0)
            {
                html.append("<div class=\"row\">");
                if (i != 0) html.append("<p/>");
                html.append(createNode(i));
            }
            else
            {
                html.append(createNode(i));
                html.append(" </div>");
            }

            if (i == fieldList.length - 1 && (i + 1) % 2 != 0)
            {
                html.append(" </div>");
            }
        }
        html.append("</form >");
        html.append("<script>").append("  $(\".sqlTime\").datetimepicker({format: 'yyyy-MM-dd',  language:  'zh'});\n" +
                "    $(\".DateTime\").datetimepicker({format: 'yyyy-MM-dd HH:mm:ss',  language:  'zh'});")
                .append("</script>");
        this.getJspContext().getOut().println(html.toString());

    }

    private String createNode(int index)
    {
        StringBuilder html = new StringBuilder();
        String labelName = titleList == null ? "" : (index >= titleList.length) ? "" : titleList[index];
        String inputName = fieldList[index];
        String charStr = StringUtils.isEmpty(labelName) ? "" : fontChar;
        Field field =
                conditionFields == null ? null : (index >= conditionFields.length) ? null : conditionFields[index];
        html.append("<div class=\"col-md-6\">").append("<div class=\"form-group \">")
                .append("<label class=\"col-sm-3 control-label text-right\" for=\"formGroupInputSmall\">" + labelName +
                        charStr + "</label>");
        if (field == null)
        {
            html.append("<div class=\"col-sm-9\">");
            html.append("<input class=\"form-control\" value='" + getFieldValue(inputName) + "' name=\"" + inputName +
                    "\" type=\"text\" id=\"" + inputName + "\" placeholder=\"" + labelName + "\"/>");
            html.append(" </div>");
        }
        else
        {
            Between between = (Between) ReflectionUtil.getFieldAnnotation(field, Between.class);
            if (between != null)
            {
                html.append("<div class=\"col-sm-4\">");
                html.append(createTime(field, between.startField(), labelName));
                html.append(" </div>");
                html.append("<div class=\"col-sm-1\">");
                html.append("<p>").append("到").append("</p>");
                html.append(" </div>");
                html.append("<div class=\"col-sm-4\">");
                html.append(createTime(field, between.endField(), labelName));
                html.append(" </div>");
                html.append(" </div>");
            }
            else
            {
                html.append("<div class=\"col-sm-9\">");
                html.append(createTime(field, inputName, labelName));
                html.append(" </div>");
                html.append(" </div>");
            }
            html.append(" </div>");
        }
        return html.toString();
    }

    private String createTime(Field field, String inputName, String titleName)
    {
        StringBuilder html = new StringBuilder();
        if (field.getType().equals(java.util.Date.class))
        {
            html.append(
                    " <input class=\"form-control DateTime\" type=\"text\" date-language='zn_CN' name='" + inputName +
                            "' value='" + getFieldValue(inputName) + "' id=\"" + inputName + "\" placeholder=\"" +
                            titleName + "\">");
            html.append(" <span class=\"add-on\"><i class=\"icon-th\"></i></span>");
        }
        else if (field.getType().getClass().equals(java.sql.Date.class))
        {
            html.append(" <input class=\"form-control sqlTime\" date-language='zn_CN' type=\"text\" value=" +
                    getFieldValue(inputName) + " name='" + inputName + "' id=\"" + inputName + "\" placeholder=\"" +
                    titleName + "\">");
            html.append(" <span class=\"add-on\"><i class=\"icon-th\"></i></span>");
        }
        else
        {
            html.append(" <input class=\"form-control\" type=\"text\" value='" + getFieldValue(inputName) + "' name='" +
                    inputName + "' id=\"" + inputName + "\" placeholder=\"" + titleName + "\">");
        }
        return html.toString();
    }

    private void init()
    {
        if (requestType == null) requestType = "get";
        if (StringUtils.isEmpty(fontChar)) fontChar = "：";
        //页面上定义了要展示的列
        if (!StringUtils.isEmpty(fields))
        {
            fieldList = fields.split(",");
        }
        //页面上定义了表头
        if (!StringUtils.isEmpty(titleNames))
        {
            titleList = titleNames.split(",");
        }
        //如果列集合还是为空那么去实体里面映射
        if (fieldList == null || titleList == null)
        {
            Field[] parment = Util.getParment(beanType);
            Object[] temp = ReflectionUtil.getFieldArray(beanType, FindKey.class);
            //对结果进行排序
            temp = Util.sortArray(temp, new SortInteface()
            {
                public boolean compare(Object obj1, Object obj2)
                {
                    if (obj1 instanceof Field && obj2 instanceof Field)
                    {
                        Field s1 = (Field) obj1;
                        Field s2 = (Field) obj2;
                        Annotation annotation1 = ReflectionUtil.getFieldAnnotation(s1, FindKey.class);
                        Annotation annotatio2 = ReflectionUtil.getFieldAnnotation(s2, FindKey.class);
                        if (annotation1 != null && annotatio2 != null)
                        {
                            return ((FindKey) annotation1).orderId() <= ((FindKey) annotatio2).orderId();
                        }
                    }
                    return false;
                }
            });
            boolean option = false;
            boolean title = false;
            if (fieldList == null)
            {
                fieldList = new String[temp.length];
                option = true;
            }
            if (!option || titleList == null)
            {
                title = true;
                titleList = new String[temp.length];
            }
            conditionFields = new Field[fieldList.length];
            for (int i = 0; i < temp.length; i++)
            {
                if (option)
                {
                    Field field = (Field) temp[i];
                    FindKey showField = (FindKey) ReflectionUtil.getFieldAnnotation(field, FindKey.class);
                    if (!StringUtils.isEmpty(showField.reflectField()))
                    {
                        fieldList[i] = field.getName() + "." + showField.reflectField();
                    }
                    else
                        fieldList[i] = field.getName();
                    conditionFields[i] = ReflectionUtil.getActualField(fieldList[i], beanType);
                }
                if (title)
                {
                    FindKey showField = (FindKey) ReflectionUtil.getFieldAnnotation((Field) temp[i], FindKey.class);
                    titleList[i] = showField.labelName();
                }

            }
        }
    }

    public String getFieldValue(String name)
    {
        HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
        String queryString = request.getQueryString();
        try
        {
            if (StringUtils.isEmpty(queryString)) return postFieldValue(name);
            queryString = URLDecoder.decode(queryString, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
        if (queryString.contains(name))
        {
            String temp = queryString.substring(queryString.indexOf(name));
            int first = temp.indexOf("=");
            int last = temp.lastIndexOf("=");
            if (first != last && first != -1)
            {
                return temp.substring(first + 1, temp.indexOf("&"));
            }
            else
            {
                if (last == first)
                {
                    return temp.substring(first + 1);
                }
                else
                {
                    return "";
                }
            }
        }
        else return postFieldValue(name);
    }

    private String postFieldValue(String name)
    {
        HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] strings = parameterMap.get(name);
        if (strings != null)
        {
            for (String str : strings)
            {
                if (StringUtils.isEmpty(str)) continue;
                else return str;
            }
        }
        return "";
    }
}
