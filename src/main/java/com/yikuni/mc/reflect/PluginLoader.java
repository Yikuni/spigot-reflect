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
    private static Boolean isMenuFacadeRegistered = false;

    public static class Settings {
        public static final Settings DefaultSettings = new Settings(true, true, false);
        private boolean banner;
        private boolean log;
        private boolean debug;

        public Settings() {
        }

        public Settings(boolean banner, boolean log, boolean debug) {
            this.banner = banner;
            this.log = log;
            this.debug = debug;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
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
        JavaPlugin plugin = JavaPlugin.getPlugin(c);
        log = plugin.getLogger();
        loadSettings = settings;
        info("Spigot Reflect starting loading " + c.getSimpleName());
        debug("Debug mode is on");
        if (settings.banner){
            InputStream inputStream = c.getResourceAsStream("/banner.txt");
            String banner = FileUtil.readFileToString(inputStream);
            if (banner != null){
                log.info("\n" + banner);
            }else {
                debug("Failed to find banner.txt");
            }
        }
        new DefaultLoaderDirector(plugin).run();
        if (!isMenuFacadeRegistered) {
            Bukkit.getPluginManager().registerEvents(new MenuFacade(), plugin);
            isMenuFacadeRegistered = true;
        }
        info("Spigot Reflect loaded " + c.getSimpleName());
    }

    public static void info(String content){
        if (loadSettings.log){
            log.info(content);
        }
    }

    public static void debug(String content){
        if (loadSettings.debug){
            log.info(content);
        }
    }
}