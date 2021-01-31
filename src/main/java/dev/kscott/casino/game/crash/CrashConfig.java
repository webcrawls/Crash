package dev.kscott.casino.game.crash;

import dev.kscott.casino.config.AbstractConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Stores Crash configuration values.
 */
public class CrashConfig extends AbstractConfig {

    /**
     * The maximum crash point.
     */
    private double maxCrashPoint = -1;

    /**
     * How long will the pre-game countdown take?
     */
    private int countdownTime = 10;

    /**
     * How long will the post-game menu run?
     */
    private int postGameTime = 5;

    /**
     * How often will the crash game tick? (in Minecrft ticks)
     */
    private int gameTick = 5;

    /**
     * How much can a player bet?
     */
    private double maxBet = 10000;

    /**
     * How fast will the multiplier increase?
     */
    private double crashSpeedMultiplier = 0.03;

    /**
     * Constructs {@link CrashConfig}.
     *
     * @param plugin {@link JavaPlugin} reference.
     */
    public CrashConfig(final @NonNull JavaPlugin plugin) {
        super(plugin, "crash/config.conf");
    }

    /**
     * Loads configuration values into memory.
     */
    @Override
    public void loadConfigurationValues() {
        this.countdownTime = this.rootNode.node("countdown-time").getInt(10);
        this.postGameTime = this.rootNode.node("post-game-time").getInt(5);
        this.gameTick = this.rootNode.node("game-tick").getInt(5);
        this.crashSpeedMultiplier = this.rootNode.node("crash-speed-multiplier").getDouble(0.03);
        this.maxBet = this.rootNode.node("max-bet").getInt(50000);
        this.maxCrashPoint = this.rootNode.node("max-crash-point").getDouble(-1D);
    }

    /**
     * @return how long the pre-game countdown should take, in seconds.
     */
    public int getCountdownTime() {
        return countdownTime;
    }

    /**
     * @return how long the post-game phase should run, in seconds.
     */
    public int getPostGameTime() {
        return postGameTime;
    }

    /**
     * @return the game tick.
     */
    public int getGameTick() {
        return gameTick;
    }

    /**
     * @return the maximum bet.
     */
    public double getMaxBet() {
        return maxBet;
    }

    /**
     * @return the crash game speed multiplier.
     */
    public double getCrashSpeedMultiplier() {
        return crashSpeedMultiplier;
    }

    /**
     * @return the maximum crash point.
     */
    public double getMaxCrashPoint() {
        return maxCrashPoint;
    }
}
