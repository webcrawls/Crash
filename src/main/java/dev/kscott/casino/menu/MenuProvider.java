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

}
