package sh.kaden.crash.menu;

import cloud.commandframework.paper.PaperCommandManager;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import sh.kaden.crash.config.Config;
import sh.kaden.crash.config.Lang;
import sh.kaden.crash.config.MenuConfig;
import sh.kaden.crash.game.crash.CrashManager;
import org.bukkit.command.CommandSender;
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

    /**
     * Config reference.
     */
    private final @NonNull Config config;

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * PaperCommandManager reference.
     */
    private final @NonNull PaperCommandManager<CommandSender> commandManager;

    /**
     * Lang reference.
     */
    private final @NonNull Lang lang;

    /**
     * GameManager reference.
     */
    private final @NonNull CrashManager crashManager;

    /**
     * MenuConfig reference.
     */
    private final @NonNull MenuConfig menuConfig;

    /**
     * A List of GameMenu that holds all opened menus.
     */
    private final @NonNull List<InventoryHolder> openInventories;

    /**
     * Constructs MenuManager.
     *
     * @param plugin         JavaPlugin reference.
     * @param commandManager PaperCommandManager reference.
     * @param config         Config reference.
     * @param lang           Lang reference.
     * @param menuConfig     MenuConfig reference.
     */
    public MenuManager(
            final @NonNull JavaPlugin plugin,
            final @NonNull PaperCommandManager<CommandSender> commandManager,
            final @NonNull CrashManager crashManager,
            final @NonNull Config config,
            final @NonNull Lang lang,
            final @NonNull MenuConfig menuConfig
    ) {
        this.plugin = plugin;
        this.menuConfig = menuConfig;
        this.commandManager = commandManager;
        this.crashManager = crashManager;
        this.openInventories = new ArrayList<>();
        this.config = config;
        this.lang = lang;
    }

    /**
     * Updates all open game menus.
     */
    public void updateMenus() {
        for (final @NonNull InventoryHolder holder : new ArrayList<>(openInventories)) {
            final @NonNull HumanEntity entity = holder.getInventory().getViewers().get(0);
            if (entity instanceof Player) {
                showGameMenu((Player) entity);
            }
        }
    }

    /**
     * Shows the active game menu to a player.
     *
     * @param player Player to show.
     */
    public void showGameMenu(final @NonNull Player player) {
        final @NonNull ChestGui menu = this.createGameMenu(player);

        this.openInventories.add(menu);

        menu.show(player);
    }

    /**
     * Creates the game menu for a player.
     *
     * @param player Player who is intended to see this.
     * @return The game menu.
     */
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

    /**
     * Removes a menu from the inventory list.
     *
     * @param inventory Inventory to remove.
     */
    public void inventoryClosed(final @NonNull Inventory inventory) {
        this.openInventories.removeIf(menu -> menu.getInventory() == inventory);
    }

}
