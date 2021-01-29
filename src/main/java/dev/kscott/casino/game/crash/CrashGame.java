package dev.kscott.casino.game.crash;

import dev.kscott.casino.game.GameType;
import dev.kscott.casino.game.TickingGame;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The {@link dev.kscott.casino.game.Game} that adds Crash functionality.
 */
public class CrashGame extends TickingGame {

    /**
     * Stores the current {@link CrashGameState}.
     */
    private @NonNull CrashGameState gameState;

    /**
     * Constructs {@link CrashGame}.
     */
    public CrashGame() {
        super("crash", GameType.CRASH, 3);
        this.gameState = CrashGameState.STOPPED;
    }

    /**
     * Runs Crash game logic.
     */
    public void runGameTick() {
        if (this.gameState == CrashGameState.STOPPED) {
            return;
        }
    }

    /**
     * Starts the game.
     *
     * @throws IllegalStateException if the game is already running.
     */
    public void startGame() throws IllegalStateException {
        if (this.gameState != CrashGameState.STOPPED) {
            throw new IllegalStateException("The game is already running!");
        }

        this.gameState = CrashGameState.PRE_GAME;
    }

    /**
     * @return the game's state.
     */
    public @NonNull CrashGameState getGameState() {
        return gameState;
    }
}
