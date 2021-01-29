package dev.kscott.casino.game;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The base Game class.
 */
public abstract class Game {

    /**
     * The id of this {@link Game}.
     */
    private final @NonNull String gameId;

    /**
     * This {@link Game}'s {@link GameType}.
     */
    private final @NonNull GameType gameType;

    /**
     * Constructs {@link Game}.
     * @param gameId The id of this {@link Game}.
     * @param gameType The {@link GameType} of this {@link Game}.
     */
    public Game(
            final @NonNull String gameId,
            final @NonNull GameType gameType
    ) {
        this.gameId = gameId;
        this.gameType = gameType;
    }

    /**
     * Executes critical pre-run code.
     */
    public abstract void setup();

    /**
     * @return the id of this {@link Game}.
     */
    public @NonNull String getGameId() {
        return gameId;
    }

    /**
     * @return the {@link GameType} of this {@link Game}.
     */
    public @NonNull GameType getGameType() {
        return gameType;
    }
}
