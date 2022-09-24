package com.yikuni.mc.reflect.common;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public abstract class Menu {
    private String name;  // 菜单名
    private final List<Inventory> inventoryList = new LinkedList<>();
    private Menu next;
    private int size;

    public Menu() {
    }

    public Menu(@NotNull String name, @NotNull Integer size) {
        this.name = name;
        this.size = size;
    }


    public abstract void click(@NotNull InventoryClickEvent event);
    public abstract void open(@NotNull Player player, @NotNull Inventory inventory, Object... args);

    public void solveOpen(String name, Player player, Object... args){
        if (this.name.equalsIgnoreCase(name)){
            Inventory inventory = Bukkit.createInventory(player, size, name);
            inventoryList.add(inventory);
            open(player, inventory, args);
            player.openInventory(inventory);
        }
        else if (next != null) next.solveOpen(name, player, args);
        else noMenuTip(name, player);
    }

    public void close(InventoryCloseEvent event){
        if (inventoryList.contains(event.getInventory())) inventoryList.remove(event.getInventory());
        else if (next != null) next.close(event);
    }

    public void setNext(Menu next) {
        this.next = next;
    }

    public void solveClick(InventoryClickEvent event){
        if (inventoryList.contains(event.getClickedInventory())){
            event.setCancelled(true);
            click(event);
        }
        else if (next != null) next.solveClick(event);
    }
    private void noMenuTip(String name, Player player){
        player.sendMessage(ChatColor.RED + "没有这个菜单: " + name);
    }

    public Menu getNext() {
        return next;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
