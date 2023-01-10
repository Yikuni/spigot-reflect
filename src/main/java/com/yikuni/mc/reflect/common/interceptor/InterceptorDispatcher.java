package com.yikuni.mc.reflect.common.interceptor;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public interface InterceptorDispatcher extends Listener {

    /**
     * register an interceptor
     * @param command   command to intercept
     * @param interceptor   interceptor
     * @param priority  priority, greater number with higher priority
     */
    void registerInterceptor(@NotNull String command ,@NotNull  Interceptor interceptor, int priority);
    void sort();
}
