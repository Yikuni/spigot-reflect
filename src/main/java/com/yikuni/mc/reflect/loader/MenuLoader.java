package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.annotation.YikuniMenu;
import com.yikuni.mc.reflect.common.Menu;
import com.yikuni.mc.reflect.context.menu.MenuFacade;

import java.lang.reflect.Method;

public class MenuLoader extends CheckLoader{
    @Override
    void loadClass(Class<?> c) throws ReflectiveOperationException {
        if (checkClass(c, Menu.class)) throw new ReflectiveOperationException("Failed to load class: " + c.getName() + " : Please implement " + Menu.class.getName());
        YikuniMenu annotation = c.getAnnotation(YikuniMenu.class);
        Menu menu = (Menu) c.newInstance();
        menu.setName(annotation.value());
        menu.setSize(annotation.size());
        MenuFacade.add(menu);
        PluginLoader.log.info("Loaded Menu: " + c.getName());
    }

    @Override
    protected void loadMethod(Method method) {
        // DO Nothing
    }

}
