package com.yikuni.mc.reflect.loader.director;

import com.yikuni.mc.reflect.annotation.YikuniCommand;
import com.yikuni.mc.reflect.annotation.YikuniEvent;
import com.yikuni.mc.reflect.annotation.YikuniMenu;
import com.yikuni.mc.reflect.annotation.YikuniRecipe;
import com.yikuni.mc.reflect.key.strategy.LowercaseReplaceStrategy;
import com.yikuni.mc.reflect.loader.CommandLoader;
import com.yikuni.mc.reflect.loader.EventLoader;
import com.yikuni.mc.reflect.loader.MenuLoader;
import com.yikuni.mc.reflect.loader.RecipeLoader;
import com.yikuni.mc.reflect.loader.builder.LoaderBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultLoaderDirector extends AbstractLoaderDirector{
    public DefaultLoaderDirector(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    void init() {
        LoaderBuilder builder = new LoaderBuilder(plugin);
        loader = builder
                .append(new EventLoader()).methodAnnotation(EventHandler.class).typeAnnotation(YikuniEvent.class)
                .append(new RecipeLoader()).methodAnnotation(YikuniRecipe.class).typeAnnotation(YikuniRecipe.class).replaceStrategy(new LowercaseReplaceStrategy())
                .append(new CommandLoader()).typeAnnotation(YikuniCommand.class)
                .append(new MenuLoader()).typeAnnotation(YikuniMenu.class)
                .build();
    }
}
