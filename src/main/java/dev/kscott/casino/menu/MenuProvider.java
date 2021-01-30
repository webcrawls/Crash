package dev.kscott.casino.menu;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a class that can provide a menu type.
 */
public interface MenuProvider {

    /**
     * @return the id of the menu to display.
     */
    @NonNull String getMenuId();

    /**
     * Called when the {@link MenuProvider} should register it's menus.
     *
     * @param menuManager The {@link MenuManager} to register menus with.
     */
    void registerMenus(@NonNull MenuManager menuManager);

    /**
     * Flips the updateMenus boolean to true, instructing the MenuManager to update menus for the next game tick.
     */
    void updateMenus();

    /**
     * @return true if the menus should be updated for the next game tick, false if not.
     */
    boolean shouldUpdateMenus();

    /**
     * Called when the {@link MenuManager} updates all open menus.
     */
    void onMenusUpdate();
}
