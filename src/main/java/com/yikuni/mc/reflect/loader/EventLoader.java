package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.context.ApplicationContext;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;

public class EventLoader extends AbstractLoader{
    @Override
    void loadClass(Class<?> c) throws ReflectiveOperationException {
        if (!checkClass(c)) throw new ReflectiveOperationException( "Failed to load class " + c.getName() + " : Please Implement Interface org.bukkit.event.Listener");
        try {
            Object instance = c.newInstance();
            plugin.getServer().getPluginManager().registerEvents((Listener) instance, plugin);
            ApplicationContext.addContext(c, instance);
            PluginLoader.log.info("Loaded Event Listener: " + c.getName());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMethod(Method method) {
        // do Nothing
    }

    private boolean checkClass(Class<?> c){
        return Listener.class.isAssignableFrom(c);
    }
}
