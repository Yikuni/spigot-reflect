package com.yikuni.mc.reflect.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInterceptor {
    // Command to intercept
    String value();
    // priority, highest num execute first
    int priority() default 0;
}
