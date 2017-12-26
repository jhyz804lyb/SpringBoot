package com.vuck.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FindKey {
    //是否是模糊查询
    String selectType() default "=";

    /**
     * 向左模糊匹配
     *
     * @return
     */
    String left() default "";

    /**
     * 向右模糊匹配
     *
     * @return
     */
    String right() default "";

    /**
     * 在作为搜索条件时的提示语例如 用户名
     *
     * @return
     */
    String labelName() default "";

    int orderId() default 0;

    String reflectField() default "";
}
