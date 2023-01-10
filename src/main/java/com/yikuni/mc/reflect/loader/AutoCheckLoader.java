package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.loader.CheckLoader;

import java.lang.reflect.Method;

public abstract class AutoCheckLoader extends CheckLoader {
    @Override
    Object loadClass(Class<?> c) throws ReflectiveOperationException {
        if (checkClass(c, getParent())) throw new ReflectiveOperationException( "Failed to load class " + c.getName() + " : Please Implement " + getParent().getName());
        else return loadCheckedClass(c);
    }

    abstract Object loadCheckedClass(Class<?> c) throws InstantiationException, IllegalAccessException;

    abstract Class<?> getParent();
}
