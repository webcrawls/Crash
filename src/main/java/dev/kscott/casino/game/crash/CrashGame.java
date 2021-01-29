package dev.kscott.casino.game.crash;

import dev.kscott.casino.game.GameType;
import dev.kscott.casino.game.TickingGame;
import dev.kscott.casino.menu.MenuManager;
import dev.kscott.casino.menu.MenuProvider;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The {@link dev.kscott.casino.game.Game} that adds Crash functionality.
 */
public class CrashGame extends TickingGame implements MenuProvider {

    /**
     * The menu id when the Crash game is stopped.
     */
    public static final String MT_STOPPED = "crash_stopped";

    /**
     * The menu id when the Crash game is in it's pre game phase.
     */
    public static final String MT_PRE_GAME = "crash_pre_game";

    /**
     * The menu id when the Crash game is running.
     */
    public static final String MT_RUNNING = "crash_running";

    /**
     * The menu id when the Crash game is in it's post game phase.
     */
    public static final String MT_POST_GAME = "crash_post_game";

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

        if (this.gameState == CrashGameState.PRE_GAME) {
            preGameCountdown--;

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

    /**
     * @return the menu id that should be displayed.
     */
    @Override
    public @NonNull String getMenuId() {
        switch (this.gameState) {
            case RUNNING:
                return MT_RUNNING;
            case STOPPED:
                return MT_STOPPED;
            case POST_GAME:
                return MT_POST_GAME;
            case PRE_GAME:
                return MT_PRE_GAME;
            default:
                return MenuManager.MT_NONE;
        }
    }
}
