package com.yikuni.mc.reflect.common.interceptor;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Interceptor {
    /**
     * execute when a player used a command, before the command take effect
     * @param player    player who used the command
     * @param args  args
     * @return  true if the command should be blocked, false if shouldn't
     */
    boolean onCommand(@NotNull Player player, @NotNull String[] args);
}
