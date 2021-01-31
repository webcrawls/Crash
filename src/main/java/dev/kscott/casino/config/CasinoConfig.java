package dev.kscott.casino.config;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        @NonNull List<String> loadedGames = new ArrayList<>();
        try {
            loadedGames = Objects.requireNonNullElse(this.rootNode.node("enabled-games").getList(String.class), new ArrayList<>());
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        this.crashEnabled = loadedGames.contains("crash");
        this.plugin.getLogger().info("Crash enabled config : "+crashEnabled);
    }

    /**
     * @return true if {@link dev.kscott.casino.game.crash.CrashGame} is enabled, false if not.
     */
    public boolean isCrashEnabled() {
        System.out.println("iscRashedNaW: "+crashEnabled);
        return crashEnabled;
    }
}
