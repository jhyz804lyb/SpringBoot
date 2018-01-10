package com.vuck.annotations.views;

import java.lang.annotation.*;

/**
 * @author liyabin
 * @date 2017/12/29
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Config
{
    Button[] Common_Btn() default {@Button(name = "search", text = "查询"), @Button(name = "add", text = "新增"),
        @Button(name = "edit", text = "修改"), @Button(name = "delete", text = "删除")};
}