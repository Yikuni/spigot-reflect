package com.yikuni.mc.reflect.common.interceptor;

import org.jetbrains.annotations.NotNull;

public class RegisteredInterceptor implements Comparable<RegisteredInterceptor>{
    private final Interceptor interceptor;
    private final int priority;

    public RegisteredInterceptor(@NotNull Interceptor interceptor, int priority) {
        this.interceptor = interceptor;
        this.priority = priority;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(@NotNull RegisteredInterceptor o) {
        return o.priority - this.priority;
    }
}
