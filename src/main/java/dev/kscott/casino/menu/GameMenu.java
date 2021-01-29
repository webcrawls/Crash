package dev.kscott.casino.menu;

import dev.kscott.casino.game.Game;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link Menu} type that has access to it's related {@link Game}.
 * @param <T>
 */
public abstract class GameMenu<T extends Game> extends Menu {

    protected final @NonNull T game;

    public GameMenu(
            final @NonNull String id,
            final @NonNull T game
    ) {
        super(id);
        this.game = game;

    }

}
