package com.yikuni.mc.reflect;

import com.yikuni.mc.reflect.context.menu.MenuFacade;
import com.yikuni.mc.reflect.loader.director.DefaultLoaderDirector;
import com.yikuni.mc.reflect.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

public class PluginLoader {
    public static Logger log;
    public static Settings loadSettings;

    public static class Settings {
        public static final Settings DefaultSettings = new Settings(true, true);
        private boolean banner;
        private boolean log;

        public Settings() {
        }

        public Settings(boolean banner, boolean log) {
            this.banner = banner;
            this.log = log;
        }

        public boolean isBanner() {
            return banner;
        }

        public void setBanner(boolean banner) {
            this.banner = banner;
        }

        public boolean isLog() {
            return log;
        }

        public void setLog(boolean log) {
            this.log = log;
        }
    }

    /**
     * start up
     *
     * @param c plugin class
     */
    public static void run(Class<? extends JavaPlugin> c) {
        run(c, Settings.DefaultSettings);
    }

    public static void run(Class<? extends JavaPlugin> c, Settings settings) {
        loadSettings = settings;
        JavaPlugin plugin = JavaPlugin.getPlugin(c);
        log = plugin.getLogger();
        if (settings.banner){
            String banner = FileUtil.readFileToString(c.getResourceAsStream("banner"));
            if (banner != null){
                System.out.println(banner);
            }
        }
        new DefaultLoaderDirector(plugin).run();
        Bukkit.getPluginManager().registerEvents(new MenuFacade(), plugin);
    }

    public static void info(String content){
        if (loadSettings.log){
            log.info(content);
        }
    }
}