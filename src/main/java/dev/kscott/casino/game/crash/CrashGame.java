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
     * Stores the pre game countdown.
     */
    private int preGameCountdown;

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

        this.running = true;
        this.gameState = CrashGameState.PRE_GAME;
        this.preGameCountdown = 10;
    }

    /**
     * Stops the game.
     *
     * @throws IllegalStateException if the game is already stopped.
     */
    public void stopGame() throws IllegalStateException {
        if (this.gameState == CrashGameState.STOPPED) {
            throw new IllegalStateException("The game is already stopped!");
        }

        this.gameState = CrashGameState.STOPPED;
        this.preGameCountdown = 0;
        this.running = false;
        // TODO return all bets
        // TODO close all inventory menus
    }

    /**
     * @return the game's state.
     */
    public @NonNull CrashGameState getGameState() {
        return gameState;
    }
}
