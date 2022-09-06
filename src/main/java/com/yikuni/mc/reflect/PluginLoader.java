package com.yikuni.mc.reflect;

import com.yikuni.mc.reflect.context.menu.MenuFacade;
import com.yikuni.mc.reflect.loader.director.DefaultLoaderDirector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PluginLoader {
    public static Logger log;

    /**
     * 启动plugin loader
     */
    public static void run(Class<? extends JavaPlugin> c){
        JavaPlugin plugin = JavaPlugin.getPlugin(c);
        log = plugin.getLogger();
        new DefaultLoaderDirector(plugin).run();

        Bukkit.getPluginManager().registerEvents(new MenuFacade(), plugin);
    }

}
