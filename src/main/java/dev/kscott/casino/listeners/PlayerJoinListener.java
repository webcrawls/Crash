package dev.kscott.casino.listeners;

import com.google.inject.Inject;
import dev.kscott.casino.game.GameType;
import dev.kscott.casino.menu.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlayerJoinListener implements Listener {

    private final @NonNull MenuManager menuManager;

    @Inject
    public PlayerJoinListener(final @NonNull MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        this.menuManager.openMenu(event.getPlayer(), GameType.CRASH);
    }

    public void onInventoryClose(final @NonNull InventoryCloseEvent event) {
        this.menuManager.closeMenu(event.getInventory());
    }

}
