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
}
