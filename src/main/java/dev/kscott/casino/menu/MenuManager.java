package dev.kscott.casino.menu;

import dev.kscott.casino.game.GameType;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
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
     * The menu type for no menu.
     */
    public static final @NonNull String MT_NONE = "none";

    /**
     * Constructs {@link MenuManager}.
     */
    public MenuManager() {
        this.gameMenuMap = new HashMap<>();
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

}
