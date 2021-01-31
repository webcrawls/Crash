package dev.kscott.casino.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

/**
 * Gives easy access to values from a language file.
 */
public class LangConfig extends AbstractConfig {

    /**
     * {@link MiniMessage} reference.
     */
    private final @NonNull MiniMessage miniMessage;

    /**
     * Constructs the config, and loads it.
     *
     * @param plugin                {@link JavaPlugin} reference
     * @param configurationFileName the filename of this config.
     */
    public LangConfig(final @NonNull JavaPlugin plugin, final @NonNull String configurationFileName) {
        super(plugin, configurationFileName);

        this.miniMessage = MiniMessage.builder().build();
    }

    /**
     * Gets the MiniMessage value at {@code key} and returns a parsed {@link Component}.
     *
     * @param key path to value.
     * @return Component.
     */
    public @NonNull Component c(final @NonNull String key) {
        return c(key, Map.of());
    }

    /**
     * Gets the String value at {@code key} and returns a parsed {@link String}.
     *
     * @param key path to value.
     * @return String.
     */
    public @NonNull String s(final @NonNull String key) {
        @Nullable String value;

        if (rootNode != null) {
            value = rootNode.node(key.split("\\.")).getString();
        } else {
            value = "<red>ERR</red>";
        }

        if (value == null) {
            this.plugin.getLogger().severe("Tried to load lang key '" + key + "', but it didn't exist.");
            this.plugin.getLogger().severe("Using default value.");
            value = "<red>ERR</red>";
        }

        return LegacyComponentSerializer.legacySection().serialize(miniMessage.parse(value));
    }

    /**
     * Gets the MiniMessage value at {@code key} and returns a parsed {@link Component}.
     * All keys in the {@code replacements} map will be replaced with their value.
     *
     * @param key          path to value.
     * @param replacements a map where the key is a placeholder, and the value is what to replace that placeholder with.
     * @return {@link Component}.
     */
    public @NonNull Component c(final @NonNull String key, final @NonNull Map<String, String> replacements) {
        @Nullable String value;

        if (rootNode != null) {
            value = rootNode.node(key.split("\\.")).getString();
        } else {
            value = "<red>ERR</red>";
        }

        if (value == null) {
            this.plugin.getLogger().severe("Tried to load lang key '" + key + "', but it didn't exist.");
            this.plugin.getLogger().severe("Using default value.");
            value = "<red>ERR</red>";
        }

        for (final Map.Entry<String, String> entry : replacements.entrySet()) {
            value = value.replace(entry.getKey(), entry.getValue());
        }

        return this.miniMessage.parse(value);
    }
}
