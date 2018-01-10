package com.vuck.annotations.views;

import java.lang.annotation.*;

/**
 * 控制列
 * @author liyabin
 * @date 2017/12/29
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Cell
{
    /**
     * 列名称
     * @return
     */
    String titleName();

    /**
     * 对应的字段
     * @return
     */
    String reflectField();

    /**
     * 列宽
     * @return
     */
    String cellWidth() default "";

    /**
     * 按钮
     * @return
     */
    String btnType() default "none";

}