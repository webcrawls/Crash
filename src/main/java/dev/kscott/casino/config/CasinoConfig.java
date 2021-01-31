package dev.kscott.casino.config;

import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;

public class CasinoConfig extends AbstractConfig {

    /**
     * Controls whether or not {@link dev.kscott.casino.game.crash.CrashGame} should be enabled.
     */
    private boolean crashEnabled = false;

    /**
     * Constructs the config, and loads it.
     *
     * @param plugin {@link JavaPlugin} reference
     */
    @Inject
    public CasinoConfig(final @NonNull JavaPlugin plugin) {
        super(plugin, "config.conf");
    }

    /**
     * Loads configuration values.
     */
    @Override
    public void loadConfigurationValues() {
        try {
            final @NonNull List<String> loadedGames = this.rootNode.node("enabled-games").getList(String.class, List.of("crash", "roulette", "coinflip"));
            this.crashEnabled = loadedGames.contains("crash");
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true if {@link dev.kscott.casino.game.crash.CrashGame} is enabled, false if not.
     */
    public boolean isCrashEnabled() {
        return crashEnabled;
    }
}
