package com.vuck.annotations;

import java.lang.annotation.*;

/**
 * 参数注解 表示这个参数在进入控制前将会自动被框架装载
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Find
{
    /**
     * 表示 是否子查询被FindKey 注解的参数为查询参数
     *
     * @return
     */
    boolean isSerachKey() default false;

    /**
     * 对应实体类的class
     *
     * @return
     */
    Class<?> entityClass() default Class.class;

    /**
     * 执行的oql语句
     *
     * @return
     */
    String OQL() default "";

    /**
     * 代理执行sql的class 一般情况下都是mybatis的接口类
     *
     * @return
     */
    Class<?> invokeClass() default Class.class;

    /**
     * 代理执行的方法
     *
     * @return
     */
    String invokeMethod() default "";
}
