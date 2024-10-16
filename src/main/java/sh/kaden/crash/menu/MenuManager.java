
package sh.kaden.crash.menu;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import sh.kaden.crash.config.Config;
import sh.kaden.crash.config.Lang;
import sh.kaden.crash.config.MenuConfig;
import sh.kaden.crash.game.crash.CrashManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the creation and displaying of menus.
 */
public class MenuManager {

    private final @NonNull Config config;
    private final @NonNull JavaPlugin plugin;
    private final @NonNull Lang lang;
    private final @NonNull CrashManager crashManager;
    private final @NonNull MenuConfig menuConfig;
    private final @NonNull List<InventoryHolder> openInventories;

    public MenuManager(
            final @NonNull JavaPlugin plugin,
            final @NonNull CrashManager crashManager,
            final @NonNull Config config,
            final @NonNull Lang lang,
            final @NonNull MenuConfig menuConfig
    ) {
        this.plugin = plugin;
        this.menuConfig = menuConfig;
        this.crashManager = crashManager;
        this.openInventories = new ArrayList<>();
        this.config = config;
        this.lang = lang;
    }

    public void updateMenus() {
        for (final @NonNull InventoryHolder holder : new ArrayList<>(openInventories)) {
            final @NonNull HumanEntity entity = holder.getInventory().getViewers().get(0);
            if (entity instanceof Player) {
                showGameMenu((Player) entity);
            }
        }
    }

    public void showGameMenu(final @NonNull Player player) {
        final @NonNull ChestGui menu = this.createGameMenu(player);

        this.openInventories.add(menu);

        menu.show(player);
    }

    private ChestGui createGameMenu(final @NonNull Player player) {
        final CrashManager.GameState gameState = this.crashManager.getGameState();

        if (gameState == CrashManager.GameState.PRE_GAME) {
            return new PreGameMenu(player, this.crashManager, config, lang, menuConfig);
        } else if (gameState == CrashManager.GameState.RUNNING) {
            return new RunningMenu(player, this.crashManager, config, lang, menuConfig);
        } else if (gameState == CrashManager.GameState.POST_GAME) {
            return new PostGameMenu(player, this.crashManager, lang, menuConfig);
        }

        return new NotRunningMenu(player);
    }

    public void inventoryClosed(final @NonNull Inventory inventory) {
        this.openInventories.removeIf(menu -> menu.getInventory() == inventory);
    }

}
