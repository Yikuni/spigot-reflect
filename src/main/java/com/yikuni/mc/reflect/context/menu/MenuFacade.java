package com.yikuni.mc.reflect.context.menu;

import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.reflect.common.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class MenuFacade implements Listener {
    private static Menu head;
    public static void open(Player player, String name, Object... args){
        if (head != null) head.solveOpen(name, player, args);
    }

    public static void add(Menu menu){
        if (head == null) {
            head = new AlertMenu();
        }
        Menu ptr = head;
        while (ptr.getNext() != null){
            ptr = ptr.getNext();
        }
        ptr.setNext(menu);
    }

    @EventHandler
    public void click(InventoryClickEvent event){
        if (event.getInventory().getType() != InventoryType.CHEST) return;
        if (head != null) head.solveClick(event);
    }

    @EventHandler
    public void click2(InventoryClickEvent event){
        if (head == null) return;
        if (event.getInventory().getType() != InventoryType.CHEST) return;
        PluginLoader.debug("clicked player inventory");
        if (head.isPlayerInteractWithMenu(event.getWhoClicked())){
            PluginLoader.debug("click2 set cancel");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event){
        if (event.getInventory().getType() != InventoryType.CHEST) return;
        if (head != null) head.close(event);
    }

    public static void alert(@NotNull Player player, @NotNull Callback callback){
        AlertMenu.callbackMap.put(player, callback);
        MenuFacade.open(player, "确认菜单");
    }


}
