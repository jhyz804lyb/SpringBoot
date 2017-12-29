package com.vuck.annotations.views;

import java.lang.annotation.*;

/**
 *
 * @author liyabin
 * @date 2017/12/29
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AutowiredSimple
{
    String[] searchFields();

    String[] showFields();

    String[] lableName();

    String[] buttons();
}