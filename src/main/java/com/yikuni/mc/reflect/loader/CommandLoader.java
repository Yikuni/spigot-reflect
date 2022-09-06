package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.annotation.YikuniCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;

public class CommandLoader extends CheckLoader{
    @Override
    void loadClass(Class<?> c) throws ReflectiveOperationException {
        if (checkClass(c, CommandExecutor.class)) throw new ReflectiveOperationException( "Failed to load class " + c.getName() + " : Please Implement Interface org.bukkit.command.CommandExecutor");
        Object o = c.newInstance();
        YikuniCommand annotation = c.getAnnotation(YikuniCommand.class);
        PluginCommand command = plugin.getCommand(annotation.value());
        if (command == null) throw new ReflectiveOperationException("Failed to load class " + c.getName() + ": Please declare command in plugin.yml first");
        command.setDescription(annotation.description());
        if (annotation.alias().length != 0){
            LinkedList<String> alias = new LinkedList<>();
            Collections.addAll(alias, annotation.alias());
            command.setAliases(alias);
        }
        command.setPermission(annotation.permission());
        command.setUsage(annotation.usage());
        command.setPermissionMessage(annotation.permissionMessage());
        command.setExecutor((CommandExecutor) o);
        if (TabCompleter.class.isAssignableFrom(c)) command.setTabCompleter((TabCompleter) o);

        PluginLoader.log.info("Loaded Command " + annotation.value());

    }

    @Override
    protected void loadMethod(Method method) {
        // do Nothing
    }
}
