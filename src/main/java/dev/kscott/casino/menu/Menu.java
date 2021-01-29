package dev.kscott.casino.menu;

import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The base abstract class for all game menus.
 */
public abstract class Menu {

    /**
     * The id of this {@link Menu}.
     */
    private final @NonNull String id;

    /**
     * Constructs {@link Menu}.
     *
     * @param id the id of this {@link Menu}.
     */
    public Menu(
            final @NonNull String id
    ) {
        this.id = id;
    }

    /**
     * Constructs a Menu, which is intended to be viewed by {@code player}.
     *
     * @param player the {@link Player} intended to view this {@link Menu}.
     * @return a newly constructed {@link Gui}.
     */
    public abstract Gui constructMenu(@NonNull Player player);

    /**
     * Shows this menu to a {@link Player}. Will call {@link this#constructMenu(Player)} before opening.
     *
     * @param viewer the {@link Player} intended to view this {@link Menu}.
     */
    public void showMenu(final @NonNull Player viewer) {
        constructMenu(viewer).show(viewer);
    }

    /**
     * @return the id of this {@link Menu}.
     */
    public @NonNull String getId() {
        return id;
    }
}
