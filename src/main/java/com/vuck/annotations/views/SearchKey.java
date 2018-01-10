package com.vuck.annotations.views;

import java.lang.annotation.*;

/**
 * @author liyabin
 * @date 2017/12/29
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface SearchKey
{
    String fieldName();

    String lableName();

    String showMessage() default "";
}