package com.yikuni.mc.reflect.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    @Nullable
    public static <T> T getContext(@NotNull Class<T> c){
        return (T)CONTEXT.get(c);
    }

    public static void addContext(@NotNull Class<?> c, @NotNull Object instance){
        CONTEXT.put(c, instance);
    }
}
