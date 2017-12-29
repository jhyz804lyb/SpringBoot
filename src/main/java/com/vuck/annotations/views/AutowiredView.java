package com.vuck.annotations.views;

import com.vuck.Inteface.CreatePageView;
import com.vuck.common.Cost;

import java.lang.annotation.*;

/**
 * @author liyabin
 * @date 2017/12/29
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AutowiredView
{
    String autowiredType() default Cost.ANNOTATION_TYPE;

    Class<? extends CreatePageView> invokeClass() default CreatePageView.class;

    String requestType() default Cost.DEFAULT_REQUEST_TYPE;

    Cell[] cells() default {};

    SearchKey[] searchKey() default {};

    Button[] buttons() default {@Button(name = "search", text = "查询"), @Button(name = "add", text = "新增"),
        @Button(name = "edit", text = "修改"), @Button(name = "delete", text = "删除")};

}