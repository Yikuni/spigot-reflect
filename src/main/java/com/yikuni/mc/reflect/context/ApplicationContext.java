package com.yikuni.mc.reflect.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    public static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    @Nullable
    public static Object getContext(@NotNull Class<?> c){
        return CONTEXT.get(c);
    }

    public static void addContext(@NotNull Class<?> c, @NotNull Object instance){
        CONTEXT.put(c, instance);
    }
}
