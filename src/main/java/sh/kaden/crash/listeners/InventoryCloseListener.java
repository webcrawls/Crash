package sh.kaden.crash.listeners;

import sh.kaden.crash.menu.GameMenu;
import sh.kaden.crash.menu.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;

public class InventoryCloseListener implements Listener {

    private final @NonNull MenuManager menuManager;

    public InventoryCloseListener(final @NonNull MenuManager menuManager)  {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onInventoryCloseEvent(final @NonNull InventoryCloseEvent event) {
        final @NonNull Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof GameMenu) {
            this.menuManager.inventoryClosed(inventory);
        }
    }

}
