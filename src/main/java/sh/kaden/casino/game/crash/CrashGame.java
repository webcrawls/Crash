package sh.kaden.casino.game.crash;

import com.google.inject.Inject;
import sh.kaden.casino.CasinoPlugin;
import sh.kaden.casino.config.LangConfig;
import sh.kaden.casino.game.GameType;
import sh.kaden.casino.game.TickingGame;
import sh.kaden.casino.game.Game;
import sh.kaden.casino.game.crash.menu.CrashPostGameMenu;
import sh.kaden.casino.game.crash.menu.CrashPreGameMenu;
import sh.kaden.casino.game.crash.menu.CrashRunningMenu;
import sh.kaden.casino.menu.MenuManager;
import sh.kaden.casino.menu.MenuProvider;
import sh.kaden.casino.utils.MathUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The {@link Game} that adds Crash functionality.
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
     * CrashProvider instance.
     */
    private @MonotonicNonNull CrashProvider crashProvider;

    /**
     * Stores the current {@link CrashGameState}.
     */
    private @NonNull CrashGameState gameState;

    /**
     * If true, the menus should update next tick.
     * If false, they shouldn't.
     */
    private boolean updateMenus;

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
     * Stores the {@link BetManager} instance.
     */
    private @MonotonicNonNull
    final BetManager betManager;

    /**
     * Stores the {@link CrashConfig}.
     */
    private @MonotonicNonNull
    final CrashConfig config;

    /**
     * Stores the {@link LangConfig}.
     */
    private @MonotonicNonNull LangConfig lang;

    /**
     * Constructs {@link CrashGame}.
     *
     * @param bukkitAudiences {@link BukkitAudiences} reference.
     * @param economy         {@link Economy} reference.
     * @param plugin          {@link JavaPlugin} reference.
     */
    @Inject
    public CrashGame(
            final @NonNull BukkitAudiences bukkitAudiences,
            final @NonNull Economy economy,
            final @NonNull JavaPlugin plugin
    ) {
        super("crash", GameType.CRASH, 3);
        this.gameState = CrashGameState.STOPPED;
        this.currentMultiplier = 0;
        this.postGameTicks = 0;
        this.preGameTicks = 0;
        this.crashPoint = 0;
        this.updateMenus = false;
        this.betManager = new BetManager(this, bukkitAudiences, economy);
        this.config = new CrashConfig(plugin);
    }

    /**
     * Sets up the game.
     */
    public void setup() {
        // TODO init betmanager
        this.crashProvider = new CrashProvider(this.config);
    }

    /**
     * Runs Crash game logic.
     */
    public void runGameTick() {
        if (this.gameState == CrashGameState.STOPPED) {
            return;
        }

        updateMenus();

        if (this.gameState == CrashGameState.PRE_GAME) {
            preGameTicks++;

            if (preGameTicks >= (CasinoPlugin.MINECRAFT_TICKS_PER_SECOND / tickSpeed) * this.config.getCountdownTime()) {
                this.crashPoint = this.crashProvider.generateCrashPoint();
                this.gameState = CrashGameState.RUNNING;
                this.currentMultiplier = 1;
                this.postGameTicks = 0;
                this.preGameTicks = 0;
            }

            return;
        }

        if (this.gameState == CrashGameState.RUNNING) {
            updateMenus();
            // thanks kevin u straight g
            if (crashPoint > currentMultiplier) {
                currentMultiplier = Math.round((currentMultiplier + (currentMultiplier * 0.03)) * 100.0) / 100.0;
            }

            if (crashPoint <= currentMultiplier) {
                this.gameState = CrashGameState.POST_GAME;
                this.postGameTicks = 0;
            }

            return;
        }

        if (this.gameState == CrashGameState.POST_GAME) {
            postGameTicks++;

            if (postGameTicks >= (CasinoPlugin.MINECRAFT_TICKS_PER_SECOND / tickSpeed) * this.config.getPostGameTime()) {
                this.gameState = CrashGameState.PRE_GAME;
                this.postGameTicks = 0;
                this.preGameTicks = 0;
            }

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
        menuManager.registerMenu(new CrashRunningMenu(this), GameType.CRASH);
        menuManager.registerMenu(new CrashPostGameMenu(this), GameType.CRASH);
    }

    /**
     * @return the game's state.
     */
    public @NonNull CrashGameState getGameState() {
        return gameState;
    }

    /**
     * @return Crash gameticks elapsed since the pre-game phase started.
     */
    public double getPreGameTicks() {
        return preGameTicks;
    }

    /**
     * @return Crash gameticks elapsed since the post-game phase started.
     */
    public double getPostGameTicks() {
        return postGameTicks;
    }

    /**
     * @return how many seconds have elapsed since the pre-game phase started. Rounded to two decimal points.
     */
    public double getPreGameSeconds() {
        return MathUtils.roundToTwoDecimalPoints(preGameTicks / (CasinoPlugin.MINECRAFT_TICKS_PER_SECOND / tickSpeed));
    }

    /**
     * @return how many seconds are left on the pre-game countdown timer. Rounded to two decimal points.
     */
    public double getPreGameSecondsLeft() {
        return MathUtils.roundToTwoDecimalPoints(this.config.getCountdownTime() - getPreGameSeconds());
    }

    /**
     * @return how many seconds have elapsed since the post-game phase started. Rounded to two decimal points.
     */
    public double getPostGameSeconds() {
        return MathUtils.roundToTwoDecimalPoints(postGameTicks / (CasinoPlugin.MINECRAFT_TICKS_PER_SECOND / tickSpeed));
    }

    /**
     * @return how many seconds are left on the post-game countdown timer. Rounded to two decimal points.
     */
    public double getPostGameSecondsLeft() {
        return MathUtils.roundToTwoDecimalPoints(this.config.getPostGameTime() - getPostGameSeconds());
    }

    /**
     * @return the current game's crash point.
     */
    public double getCrashPoint() {
        return crashPoint;
    }

    /**
     * @return the game's current multiplier.
     */
    public double getCurrentMultiplier() {
        return currentMultiplier;
    }

    /**
     * Flips the updateMenus boolean to true, instructing the MenuManager to update menus for the next game tick.
     */
    public void updateMenus() {
        this.updateMenus = true;
    }

    /**
     * @return true if the menus should be updated for the next game tick, false if not.
     */
    public boolean shouldUpdateMenus() {
        return this.updateMenus;
    }

    /**
     * Called when the {@link MenuManager} updates all open menus.
     */
    public void onMenusUpdate() {
        this.updateMenus = false;
    }
}
