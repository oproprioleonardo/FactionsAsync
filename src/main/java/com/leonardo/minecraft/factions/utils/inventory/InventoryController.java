package com.leonardo.minecraft.factions.utils.inventory;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.function.Consumer;

@Singleton
public class InventoryController implements Listener {

    @Getter
    private static final HashMap<InventoryMaker, Consumer<InventoryClickEvent>> consumersOnClick;
    @Getter
    private static final HashMap<InventoryMaker, Consumer<InventoryCloseEvent>> consumersOnClose;

    static {
        consumersOnClick = Maps.newHashMap();
        consumersOnClose = Maps.newHashMap();
    }

    @Inject
    private Plugin plugin;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        for (InventoryMaker inventoryMaker : consumersOnClick.keySet()) {
            final Consumer<InventoryClickEvent> consumer = consumersOnClick.get(inventoryMaker);
            final Inventory inventory = inventoryMaker.getInventory();
            if (inventory == null) return;
            if (inventory.equals(event.getInventory())) {
                if (consumer != null) {
                    if (event.getInventory() != null &&
                        event.getInventory().equals(event.getWhoClicked().getInventory()))
                        consumer.accept(event);
                }
                if (inventoryMaker.isCancellable()) event.setCancelled(true);
                if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
                final Item item = inventoryMaker.getItems().get(event.getRawSlot());
                if (item == null) return;
                if (item.isCancellable()) event.setCancelled(true);
                if (item.getClickEventConsumer() != null) item.getClickEventConsumer().accept(event);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        consumersOnClose.forEach((inventoryMaker, consumer) -> {
            if (!inventoryMaker.getInventory().equals(event.getInventory())) return;
            if (consumer != null) consumer.accept(event);
            if (inventoryMaker.isUpgradeable())
                inventoryMaker.getSchID().forEach(integer -> Bukkit.getScheduler().cancelTask(integer));
            if (event.getInventory().getViewers().size() == 1) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    getConsumersOnClick().remove(inventoryMaker);
                    getConsumersOnClose().remove(inventoryMaker);
                }, 5L);
            }
        });
    }


}
