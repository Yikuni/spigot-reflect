package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.annotation.YikuniRecipe;
import com.yikuni.mc.reflect.key.strategy.NoneStrategy;
import com.yikuni.mc.reflect.key.strategy.StringReplaceStrategy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 装在Recipe
 */
public class RecipeLoader extends AbstractLoader{
    private StringReplaceStrategy strategy = new NoneStrategy();

    @Override
    AbstractLoader loadClass(Class<?> c) {
        // doNothing
        return null;
    }

    @Override
    protected void loadMethod(Method method) {
        if (!checkMethod(method)){
            throw new IllegalArgumentException("Failed to load recipe " + method.getName() + " :" + "Illegal Argument Provided");
        }
        Class<?> clazz = method.getDeclaringClass();
        try {
            Object instance = clazz.newInstance();
            YikuniRecipe annotation = method.getAnnotation(YikuniRecipe.class);
            String value = annotation.value();
            String name;
            if (value.equals("")){
                name = strategy.replace(method.getName());
            }else {
                name = value;
            }
            NamespacedKey key = new NamespacedKey(plugin, name);
            Object invoke = method.invoke(instance, key);
            Bukkit.addRecipe((Recipe) invoke);
            info("Loaded Recipe: " + name);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setReplaceStrategy(StringReplaceStrategy strategy) {
        this.strategy = strategy;
    }

    private boolean checkMethod(Method method){
        Class<?>[] params = method.getParameterTypes();
        if (params.length == 1 && params[0].equals(NamespacedKey.class)){
            return Recipe.class.isAssignableFrom(method.getReturnType());
        }
        return false;
    }
}
