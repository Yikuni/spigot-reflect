package com.yikuni.mc.reflect.loader.director;

import com.yikuni.mc.reflect.annotation.*;
import com.yikuni.mc.reflect.key.strategy.LowercaseReplaceStrategy;
import com.yikuni.mc.reflect.loader.*;
import com.yikuni.mc.reflect.loader.builder.LoaderBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultLoaderDirector extends AbstractLoaderDirector{
    private InterceptorLoader interceptorLoader;
    public DefaultLoaderDirector(JavaPlugin plugin) {
        super(plugin);

    }

    @Override
    void init() {
        interceptorLoader = new InterceptorLoader();
        LoaderBuilder builder = new LoaderBuilder(plugin);
        loader = builder
                .append(new EventLoader()).methodAnnotation(EventHandler.class).typeAnnotation(YikuniEvent.class)
                .append(new RecipeLoader()).methodAnnotation(YikuniRecipe.class).typeAnnotation(YikuniRecipe.class).replaceStrategy(new LowercaseReplaceStrategy())
                .append(new CommandLoader()).typeAnnotation(YikuniCommand.class)
                .append(new MenuLoader()).typeAnnotation(YikuniMenu.class)
                .append(interceptorLoader).typeAnnotation(CommandInterceptor.class)
                .build();
    }

    @Override
    void onLoaded() {
        interceptorLoader.sort();
    }
}
