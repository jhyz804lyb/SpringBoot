package com.vuck.annotations;

import java.lang.annotation.*;

@Target( {ElementType.TYPE, ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
@Documented
public @interface ImageInfo
{
}
