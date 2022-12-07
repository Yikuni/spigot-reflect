package com.yikuni.mc.reflect.loader.director;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.loader.AbstractLoader;
import com.yikuni.mc.reflect.util.YikuniReflectUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public abstract class AbstractLoaderDirector {
    protected AbstractLoader loader;
    protected JavaPlugin plugin;

    public AbstractLoaderDirector(JavaPlugin plugin){
        this.plugin = plugin;
        init();
    }

    abstract void init();

    public void run(){
        Set<Class<?>> classes = YikuniReflectUtil.getClasses(plugin.getClass().getPackage().getName(), true);
        PluginLoader.debug("Totally scanned classes : " + classes.size());
        classes.forEach(c-> loader.resolve(c));
        loader.loadClass();
    }
}
