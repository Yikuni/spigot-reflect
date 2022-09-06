package com.yikuni.mc.reflect.context.menu;

import com.yikuni.mc.reflect.common.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuFacade implements Listener {
    private static Menu head;
    public static void open(Player player, String name){
        if (head != null) head.solveOpen(name, player);
    }

    public static void add(Menu menu){
        if (head == null) head = menu;
        else {
            Menu ptr = head;
            while (ptr.getNext() != null){
                ptr = ptr.getNext();
            }
            ptr.setNext(menu);
        }
    }

    @EventHandler
    public void click(InventoryClickEvent event){
        if (head != null) head.solveClick(event);
    }

    @EventHandler
    public void close(InventoryCloseEvent event){
        if (head != null) head.close(event);
    }


}
