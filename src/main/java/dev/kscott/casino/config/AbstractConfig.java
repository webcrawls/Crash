package dev.kscott.casino.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.nio.file.Paths;

/**
 * Defines a configuration object.
 */
public abstract class AbstractConfig {

    /**
     * The root configuration node.
     */
    protected @MonotonicNonNull ConfigurationNode rootNode;

    /**
     * The {@link JavaPlugin} reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The filename of this config.
     */
    private final @NonNull String configurationFileName;

    /**
     * Constructs the config, and loads it.
     *
     * @param plugin                {@link JavaPlugin} reference
     * @param configurationFileName the filename of this config.
     */
    public AbstractConfig(
            final @NonNull JavaPlugin plugin,
            final @NonNull String configurationFileName
    ) {
        this.plugin = plugin;
        this.configurationFileName = configurationFileName;

        // Save config to file if it doesn't already exist
        if (!new File(this.plugin.getDataFolder(), configurationFileName).exists()) {
            plugin.saveResource(configurationFileName, false);
        }

        // Load the config
        this.loadConfig();
        this.loadConfigurationValues();
    }

    /**
     * Loads the config into the {@link this.root} node.
     */
    private void loadConfig() {
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .path(Paths.get(plugin.getDataFolder().getAbsolutePath(), configurationFileName))
                .build();

        try {
            rootNode = loader.load();
        } catch (final ConfigurateException e) {
            throw new RuntimeException("Failed to load the configuration, filename: '" + configurationFileName + "'.", e);
        }
    }

    /**
     * Loads configuration values.
     * Called during construction and during {@link this#reload()}.
     */
    protected void loadConfigurationValues() {

    }

    /**
     * Reloads this config.
     */
    public void reload() {
        this.loadConfig();
        this.loadConfigurationValues();
    }


}
