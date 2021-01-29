package dev.kscott.casino.game.crash;

/**
 * Identifies {@link CrashGame}'s state.
 */
public enum CrashGameState {
    /**
     * The state when the game is stopped.
     */
    STOPPED,
    /**
     * The state when the game is about to start.
     */
    PRE_GAME,
    /**
     * The state when the game is running.
     */
    RUNNING,
    /**
     * The game when the game has finished (i.e. the "Crashed at 1.0x" menu)
     */
    POST_GAME
}
