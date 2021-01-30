package dev.kscott.casino.menu;

import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import dev.kscott.casino.game.GameType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class that stores {@link Menu}s and shows them to {@link Player}s.
 */
public class MenuManager {

    /**
     * Ties {@link Menu}s to their {@link GameType}.
     */
    private final @NonNull Map<GameType, Map<String, Menu>> gameMenuMap;

    /**
     * Ties {@link GameType}s to a {@link MenuProvider}.
     */
    private final @NonNull Map<GameType, MenuProvider> providerMap;

    /**
     * Ties {@link GameType}s to a list of open inventories using {@link InventoryHolder}s.
     */
    private final @NonNull Map<GameType, List<InventoryHolder>> openInventoryHolderMap;

    /**
     * The menu type for no menu.
     */
    public static final @NonNull String MT_NONE = "none";

    /**
     * Constructs {@link MenuManager}.
     */
    public MenuManager() {
        this.gameMenuMap = new HashMap<>();
        this.providerMap = new HashMap<>();
        this.openInventoryHolderMap = new HashMap<>();
    }

    /**
     * Registers a {@link MenuProvider}.
     *
     * @param gameType     {@link GameType} to associate this {@link MenuProvider} with.
     * @param menuProvider {@link MenuProvider} instance.
     */
    public void registerProvider(final @NonNull GameType gameType, final @NonNull MenuProvider menuProvider) {
        this.providerMap.put(gameType, menuProvider);
        menuProvider.registerMenus(this);
    }

    /**
     * Registers a {@link Menu}, ties it to the given {@link GameType}.
     * <p>
     * If the menu id already exists ({@link Menu#getId()}, it will be overwritten.
     *
     * @param menu     {@link Menu} to register.
     * @param gameType {@link GameType} to associate with {@code menu}.
     */
    public void registerMenu(final @NonNull Menu menu, final @NonNull GameType gameType) {
        if (!this.gameMenuMap.containsKey(gameType)) {
            this.gameMenuMap.put(gameType, new HashMap<>());
        }

        final @NonNull String id = menu.getId();

        final @NonNull Map<String, Menu> map = this.gameMenuMap.get(gameType);

        map.put(id, menu);
    }

    /**
     * Returns a menu using {@code gameType} and {@code id}.
     *
     * @param gameType GameType of the registered {@link Menu}.
     * @param id       id of the registered {@link Menu}.
     * @return {@link Menu}. May be null.
     */
    public @Nullable Menu getMenu(final @NonNull GameType gameType, final @NonNull String id) {
        if (!this.gameMenuMap.containsKey(gameType)) {
            return null;
        }

        final @NonNull Map<String, Menu> map = this.gameMenuMap.get(gameType);

        return map.get(id);
    }

    /**
     * Returns a {@link Menu} with the given {@link GameType}, accessing the {@link MenuProvider} associated with {@code gameType}.
     *
     * @param gameType {@link GameType} of the {@link MenuProvider}.
     * @return {@link Menu}. May be null if no provider was found, or no menu was returned.
     */
    public @Nullable Menu getMenu(final @NonNull GameType gameType) {
        final @Nullable MenuProvider menuProvider = this.providerMap.get(gameType);

        if (menuProvider == null) {
            return null;
        }

        final @NonNull String menuId = menuProvider.getMenuId();

        final @Nullable Menu menu = this.getMenu(gameType, menuId);

        return menu;
    }

    /**
     * Opens a menu for a {@link Player}, using the given {@link GameType}.
     * <p>
     * If the menu is null, nothing will be opened.
     *
     * @param player   {@link Player} to open menu for.
     * @param gameType the {@link GameType} associated with this {@link Menu}.
     */
    public void openMenu(final @NonNull Player player, final @NonNull GameType gameType) {
        final @Nullable Menu menu = this.getMenu(gameType);

        if (menu == null) {
            return;
        }

        System.out.println(player.getName());

        final @NonNull Gui gui = menu.showMenu(player);

        this.openInventoryHolderMap.computeIfAbsent(gameType, val -> new ArrayList<>());

        final @NonNull List<InventoryHolder> openInventories = this.openInventoryHolderMap.get(gameType);

        openInventories.add(gui);
    }

    /**
     * Closes a menu, removing all references to it in internal stores.
     * <p>
     * Will do nothing if {@code inventory} is not a {@link GameMenu}.
     *
     * @param inventory the inventory to close.
     */
    public void closeMenu(final @NonNull Inventory inventory) {
        final @Nullable InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof GameMenu)) {
            return;
        }

        final @NonNull GameMenu<?> menu = (GameMenu<?>) inventory.getHolder();

        final @NonNull GameType gameType = menu.getGameType();

        final @Nullable List<InventoryHolder> inventoryList = this.openInventoryHolderMap.get(gameType);

        if (inventoryList == null) {
            return;
        }

        inventoryList.remove(menu);
    }

    /**
     * Updates all menus for a given {@link GameType}.
     *
     * @param gameType {@link GameType} to update menus for.
     */
    public void updateMenus(final @NonNull GameType gameType) {
        final @Nullable MenuProvider menuProvider = this.providerMap.get(gameType);

        if (menuProvider == null) {
            return;
        }

        final @Nullable List<InventoryHolder> openInventories = this.openInventoryHolderMap.get(gameType);

        if (openInventories == null) {
            return;
        }

        for (final @NonNull InventoryHolder holder : new ArrayList<>(openInventories)) {
            for (final @NonNull HumanEntity entity : new ArrayList<>(holder.getInventory().getViewers())) {
                 if (entity instanceof Player) {
                     this.openMenu(((Player) entity), gameType);
                 }

            }
        }
    }

}
