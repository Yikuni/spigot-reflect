package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.annotation.CommandInterceptor;
import com.yikuni.mc.reflect.common.interceptor.Interceptor;
import com.yikuni.mc.reflect.common.interceptor.InterceptorDispatcher;

import java.lang.reflect.Method;

public class InterceptorLoader extends AutoCheckLoader{

    private final InterceptorDispatcher dispatcher;

    public InterceptorLoader() {
        dispatcher = PluginLoader.interceptorDispatcher;
    }


    @Override
    protected void loadMethod(Method method) {
        // do nothing
    }

    @Override
    Object loadCheckedClass(Class<?> c) throws InstantiationException, IllegalAccessException {
        Interceptor o = (Interceptor) c.newInstance();
        CommandInterceptor annotation = c.getAnnotation(CommandInterceptor.class);
        dispatcher.registerInterceptor(annotation.value(), o, annotation.priority());
        info("Loaded interceptor " + c.getName());
        return o;
    }

    @Override
    Class<?> getParent() {
        return Interceptor.class;
    }

    public void sort(){
        dispatcher.sort();
    }
}
