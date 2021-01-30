package dev.kscott.casino.game.crash;

import dev.kscott.casino.game.GameType;
import dev.kscott.casino.game.TickingGame;
import dev.kscott.casino.game.crash.menu.CrashPreGameMenu;
import dev.kscott.casino.menu.MenuManager;
import dev.kscott.casino.menu.MenuProvider;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
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

    private static final int PRE_GAME_LENGTH = 10;

    private static final int POST_GAME_LENGTH = 5;

    private static final int MINECRAFT_TICKS_PER_SECOND = 20;

    /**
     * CrashProvider instance.
     */
    private @MonotonicNonNull CrashProvider crashProvider;

    /**
     * Stores the current {@link CrashGameState}.
     */
    private @NonNull CrashGameState gameState;

    /**
     * Stores the pre game countdown.
     */
    private int preGameTicks;

    /**
     * Stores the crash point for the current game.
     */
    private double crashPoint;

    /**
     * Stores the current multiplier for the current game.
     */
    private double currentMultiplier;

    /**
     * Stores how many seconds have elapsed for the post game phase.
     */
    private double postGameTicks;

    /**
     * Constructs {@link CrashGame}.
     */
    public CrashGame() {
        super("crash", GameType.CRASH, 3);
        this.gameState = CrashGameState.STOPPED;
        this.currentMultiplier = 0;
        this.postGameTicks = 0;
        this.preGameTicks = 0;
        this.crashPoint = 0;
    }

    /**
     * Sets up the game.
     */
    public void setup() {
        // TODO init betmanager
        this.crashProvider = new CrashProvider();
    }

    /**
     * Runs Crash game logic.
     */
    public void runGameTick() {
        if (this.gameState == CrashGameState.STOPPED) {
            return;
        }

        if (this.gameState == CrashGameState.PRE_GAME) {
            Bukkit.broadcastMessage("pregame: seconds left: "+(PRE_GAME_LENGTH - (preGameTicks / (MINECRAFT_TICKS_PER_SECOND / tickSpeed))));
            preGameTicks++;

            if (preGameTicks >= (MINECRAFT_TICKS_PER_SECOND / tickSpeed)*PRE_GAME_LENGTH) {
                this.crashPoint = this.crashProvider.generateCrashPoint();
                this.gameState = CrashGameState.RUNNING;
                this.currentMultiplier = 1;
                this.postGameTicks = 0;
                this.preGameTicks = 0;
            }
        }

        if (this.gameState == CrashGameState.RUNNING) {
            // thanks kevin u straight g
            if (crashPoint > currentMultiplier) {
                currentMultiplier = Math.round((currentMultiplier + (currentMultiplier * 0.03)) * 100.0) / 100.0;
                Bukkit.broadcastMessage("Running: " + currentMultiplier + "x");
            }

            if (crashPoint <= currentMultiplier) {
                Bukkit.broadcastMessage("crashed at " + crashPoint + "x! ");
                this.gameState = CrashGameState.POST_GAME;
                this.postGameTicks = 0;
            }
        }

        if (this.gameState == CrashGameState.POST_GAME) {
            postGameTicks++;

            Bukkit.broadcastMessage("postgame: seconds left: "+(POST_GAME_LENGTH-(postGameTicks/(MINECRAFT_TICKS_PER_SECOND / tickSpeed))));

            if (postGameTicks >= (MINECRAFT_TICKS_PER_SECOND / tickSpeed)*POST_GAME_LENGTH) {
                this.gameState = CrashGameState.PRE_GAME;
                this.postGameTicks = 0;
                this.preGameTicks = 0;
            }
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
        this.preGameTicks = 10;
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
        this.preGameTicks = 0;
        this.running = false;
        // TODO return all bets
        // TODO close all inventory menus
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

    /**
     * Registers menus with {@code menuManager}.
     *
     * @param menuManager The {@link MenuManager} to register menus with.
     */
    public void registerMenus(final @NonNull MenuManager menuManager) {
        menuManager.registerMenu(new CrashPreGameMenu(this), GameType.CRASH);
    }

    /**
     * @return the game's state.
     */
    public @NonNull CrashGameState getGameState() {
        return gameState;
    }

    /**
     * @return the pre-game countdown.
     */
    public int getPreGameTicks() {
        return preGameTicks;
    }
}
