package com.yikuni.mc.reflect.context.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MenuHolder implements InventoryHolder {
    private final Inventory inventory;
    private final Player player;

    public MenuHolder(Inventory inventory, @NotNull Player player) {
        this.inventory = inventory;
        this.player = player;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }
}
