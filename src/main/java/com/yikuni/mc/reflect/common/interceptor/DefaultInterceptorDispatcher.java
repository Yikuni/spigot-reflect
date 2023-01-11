package com.yikuni.mc.reflect.common.interceptor;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DefaultInterceptorDispatcher implements InterceptorDispatcher{
    private final Map<String, List<RegisteredInterceptor>> interceptorMap = new HashMap<>();

    @Override
    public void registerInterceptor(@NotNull String command, @NotNull Interceptor interceptor, int priority) {
        RegisteredInterceptor registeredInterceptor = new RegisteredInterceptor(interceptor, priority);
        if (interceptorMap.containsKey(command)){
            interceptorMap.get(command).add(registeredInterceptor);
        }else {
            LinkedList<RegisteredInterceptor> list = new LinkedList<>();
            list.add(registeredInterceptor);
            interceptorMap.put(command, list);
        }
    }

    @Override
    public void sort() {
        interceptorMap.forEach((k, v) -> Collections.sort(v));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event){
        String command = event.getMessage().substring(1, event.getMessage().indexOf(' '));
        List<RegisteredInterceptor> interceptorList = interceptorMap.get(command);
        if (interceptorList != null){
            String[] s = event.getMessage().split(" ");
            String[] args = new String[s.length - 1];
            System.arraycopy(s, 1, args, 0, s.length - 1);
            for (RegisteredInterceptor registeredInterceptor : interceptorList) {
                if (registeredInterceptor.getInterceptor().onCommand(event.getPlayer(), args)){
                    event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to use this command");
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }



}
