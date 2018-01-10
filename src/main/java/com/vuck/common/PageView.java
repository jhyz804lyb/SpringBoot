package com.vuck.common;

import com.vuck.annotations.views.*;
import com.vuck.common.views.Button;
import com.vuck.utils.StringUtils;

/**
 * 页面信息封装
 *
 * @author liyabin
 * @date 2017/12/29
 */
public class PageView
{
    /**
     * 请求类型
     */
    private String requestType;
    /**
     * 加载实体类的class
     */
    private Class<?> beanType;
    /**
     * 展示的字段
     */
    private String[] fieldList;
    /**
     * 展示的列
     */
    private String[] cellNames;
    /**
     * 查询条件字段
     */
    private String[] searchKeys;
    /**
     * 空间按钮
     */
    private Button[] buttons;
    /**
     * 列宽
     */
    private String[] cellWidths;

    public PageView()
    {

    }

    public PageView(AutowiredView view)
    {

    }

    public PageView(AutowiredSimple view)
    {
        beanType = view.beanType();
    }

    public String getRequestType()
    {
        if (StringUtils.isEmpty(requestType))
            requestType = Cost.DEFAULT_REQUEST_TYPE;
        return requestType;
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }

    public Class<?> getBeanType()
    {
        return beanType;
    }

    public void setBeanType(Class<?> beanType)
    {
        this.beanType = beanType;
    }

    public String[] getFieldList()
    {
        return fieldList;
    }

    public void setFieldList(String[] fieldList)
    {
        this.fieldList = fieldList;
    }

    public String[] getCellNames()
    {
        return cellNames;
    }

    public void setCellNames(String[] cellNames)
    {
        this.cellNames = cellNames;
    }

    public String[] getSearchKeys()
    {
        return searchKeys;
    }

    public void setSearchKeys(String[] searchKeys)
    {
        this.searchKeys = searchKeys;
    }

    public Button[] getButtons()
    {
        return buttons;
    }

    public void setButtons(Button[] buttons)
    {
        this.buttons = buttons;
    }

    public String[] getCellWidths()
    {
        return cellWidths;
    }

    public void setCellWidths(String[] cellWidths)
    {
        this.cellWidths = cellWidths;
    }
}
