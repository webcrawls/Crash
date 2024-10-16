package sh.kaden.casino.menu;

import sh.kaden.casino.game.Game;
import sh.kaden.casino.game.GameType;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link Menu} type that has access to it's related {@link Game}.
 *
 * @param <T> {@link Game} associated with this {@link GameMenu}.
 */
public abstract class GameMenu<T extends Game> extends Menu {

    /**
     * The {@link GameType} associated with this meny.
     */
    private final @NonNull GameType gameType;

    /**
     * Reference to the {@link Game}.
     */
    protected final @NonNull T game;

    /**
     * Constructs {@link GameMenu}.
     *
     * @param id   id of this menu.
     * @param game the {@link Game} associated with this menu.
     */
    public GameMenu(
            final @NonNull String id,
            final @NonNull T game
    ) {
        super(id);
        this.game = game;
        this.gameType = game.getGameType();
    }

    /**
     * @return this {@link GameMenu}'s {@link GameType}.
     */
    public @NonNull GameType getGameType() {
        return gameType;
    }
}
