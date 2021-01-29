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
    @Override
    public void runGameTick() {
        if (this.gameState == CrashGameState.STOPPED) {
            return;
        }
    }

    /**
     * @return the game's state.
     */
    public @NonNull CrashGameState getGameState() {
        return gameState;
    }
}
