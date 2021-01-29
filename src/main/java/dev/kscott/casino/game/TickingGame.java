package dev.kscott.casino.game;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The class for {@link Game}s that have a game tick.
 */
public abstract class TickingGame extends Game {

    /**
     * Controls how fast this game will tick. Measured in Minecraft ticks.
     */
    private final int tickSpeed;

    /**
     * If false, this game is not running and should not be ticked.
     * If true, this game is running and should be ticked.
     */
    protected boolean running;

    /**
     * Constructs {@link TickingGame}.
     *
     * @param gameId    The id of this {@link TickingGame}.
     * @param gameType  The {@link GameType} of this {@link TickingGame}.
     * @param tickSpeed The speed of this game's tick, in Minecraft ticks.
     */
    public TickingGame(
            final @NonNull String gameId,
            final @NonNull GameType gameType,
            final int tickSpeed
    ) {
        super(gameId, gameType);
        this.tickSpeed = tickSpeed;
        this.running = false;
    }

    /**
     * Runs this game's tick.
     */
    public abstract void runGameTick();

    /**
     * Starts this game.
     */
    public abstract void startGame();

    /**
     * Stops this game.
     */
    public abstract void stopGame();

    /**
     * @return how fast this game should tick, measured in Minecraft ticks.
     */
    public int getTickSpeed() {
        return tickSpeed;
    }
}
