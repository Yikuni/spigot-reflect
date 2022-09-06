package com.yikuni.mc.reflect.loader.builder;

import com.yikuni.mc.reflect.key.strategy.StringReplaceStrategy;
import com.yikuni.mc.reflect.loader.AbstractLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class LoaderBuilder {
    private final List<AbstractLoader> loaderList = new ArrayList<>();
    private final JavaPlugin plugin;

    public LoaderBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * 设置下一个装载器
     * @param loader    下一个装载器
     * @return  builder
     */
    public LoaderBuilder append(AbstractLoader loader){
        loader.setPlugin(plugin);
        if (loaderList.size() != 0) {
            // 如果不是第一个
            AbstractLoader previous = loaderList.get(loaderList.size() - 1);
            previous.setNext(loader);
        }
        loaderList.add(loader);
        return this;
    }

    /**
     * 设置当前装载器的typeAnnotation
     * @param c typeAnnotation的类
     * @return  builder
     */
    public LoaderBuilder typeAnnotation(Class<? extends Annotation> c){
        AbstractLoader current = loaderList.get(loaderList.size() - 1);
        current.setTypeAnnotation(c);
        return this;
    }

    /**
     * 设置当前装载器的methodAnnotation
     * @param c methodAnnotation的类
     * @return  builder
     */
    public LoaderBuilder methodAnnotation(Class<? extends Annotation> c){
        AbstractLoader current = loaderList.get(loaderList.size() - 1);
        current.setMethodAnnotation(c);
        return this;
    }

    public LoaderBuilder replaceStrategy(StringReplaceStrategy strategy){
        AbstractLoader current = loaderList.get(loaderList.size() - 1);
        current.setReplaceStrategy(strategy);
        return this;
    }

    public AbstractLoader build(){
        return loaderList.get(0);
    }



}
