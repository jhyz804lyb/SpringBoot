package com.vuck.common;

/**
 * 导出接口用于校验
 * @author liyabin
 * @date 2017/11/9
 */
public interface ExcelInfo
{

    public String fileName();

    public String[]titleList();

    public int[]dataType();

    public int []cellWidth();
}