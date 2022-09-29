package com.yikuni.mc.reflect.annotation;


import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YikuniCommand {

    @NotNull
    String value(); // 指令名

    @Deprecated
    String[] alias() default {};   // 别名

    String description() default "The command have no description";

    String permission() default "op";

    String permissionMessage() default "You are not allowed to use this command";

    String usage() default "";


}
