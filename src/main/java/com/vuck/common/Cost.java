package com.vuck.common;

public class Cost
{
    public static boolean IS_INJECT = false;
    public static String lastInitTime = null;
    public static String FORWARD_NAME = "ISFORWARDED";
    public static final String PAGE_TGP = "base";
    public static final String PAGE_ATTR_NAME = "base";
    public static final String EXCEL_NO_DEFINE_FILENAME = "DEFINE_FILE_NAME";
    public static final String LOGIN_USER = "LOGIN_USER_INFO";
    public static final String FRAME_PARAM = "REQUEST_PARAM";
    /**
     * 请求bean的类型
     */
    public static final String BEAN_TYPE = "BEAN_TYPE";

    /**
     * 请求bean的类型
     */
    public static final String PAGE_INFO = "PAGE_INFO";

    public static final String REQUEST_URL = "REQUEST_URL";

    public static final String COMMON_PAGE = "base/base";

    /**
     * 生成页面时候这么产生类型是通过注解还是通过代理方法(通过注解还)
     */
    public static final String ANNOTATION_TYPE = "CREATE_BY_ANNOTATION";
    /**
     * 通过代理方法
     */
    public static final String INVOKE_TYPE = "CREATE_BY_ANNOTATION";

    /**
     * 默认请求方式
     */
    public static final String DEFAULT_REQUEST_TYPE = "POST";

}
