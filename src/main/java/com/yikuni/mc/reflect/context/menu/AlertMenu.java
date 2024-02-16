package com.yikuni.mc.reflect.context.menu;

import com.yikuni.mc.reflect.common.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AlertMenu extends Menu {

    public AlertMenu() {
        setSize(9);
        setName("确认菜单");
    }

    protected static final Map<Player, Callback> callbackMap = new HashMap<>();

    @Override
    public void click(@NotNull InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == 2){
            player.closeInventory();
            callbackMap.get(player).callback();
        } else if (event.getSlot() == 6) {
            player.closeInventory();
            callbackMap.get(player).callback();
        }
    }

    @Override
    public void open(@NotNull Player player, @NotNull Inventory inventory, Object... args) {
        ItemStack green = new ItemStack(Material.GREEN_WOOL);
        ItemMeta itemMeta = green.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("确认");
        green.setItemMeta(itemMeta);

        ItemStack red = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta1 = red.getItemMeta();
        assert itemMeta1 != null;
        itemMeta1.setDisplayName("取消");
        red.setItemMeta(itemMeta1);

        inventory.setItem(2, green);
        inventory.setItem(6, red);
    }
}
