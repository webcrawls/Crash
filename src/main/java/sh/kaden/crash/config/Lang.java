package sh.kaden.crash.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Map;

/**
 * Stores the Quantum lang and handles MiniMessage stuff.
 */
public class Lang {

    private final @NonNull JavaPlugin plugin;

    /**
     * The root ConfigurationNode.
     */
    private @Nullable ConfigurationNode root;

    public Lang(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;

        // Save config to file if it doesn't already exist
        if (!Files.exists(Path.of(this.plugin.getDataFolder().getAbsolutePath(), "lang.yml"))) {
            plugin.saveResource("lang.yml", false);
        }

        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(Paths.get(plugin.getDataFolder().getAbsolutePath(), "lang.yml"))
                .build();

        try {
            root = loader.load();
        } catch (ConfigurateException e) {
            this.plugin.getLogger().severe("There was an error loading the lang:");
            e.printStackTrace();
            this.plugin.getLogger().severe("QuantumWild will continue loading, but you should really fix this.");
        }
    }


    /**
     * Gets the MiniMessage value at {@code key} and returns a parsed Component
     *
     * @param key path to value
     * @return Component
     */
    public @NonNull Component c(final @NonNull String key) {
        return c(key, Map.of());
    }

    /**
     * Gets the String value at {@code key} and returns a parsed String.
     *
     * @param key path to value.
     * @return String
     */
    public @NonNull String s(final @NonNull String key) {
        @Nullable String value;

        if (root != null) {
            value = root.node(key.split("\\.")).getString();
        } else {
            value = "<red>ERR</red>";
        }

        if (value == null) {
            this.plugin.getLogger().severe("Tried to load lang key '" + key + "', but it didn't exist.");
            this.plugin.getLogger().severe("Using default value.");
            value = "<red>ERR</red>";
        }

        return LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize(value));
    }

    /**
     * Gets the MiniMessage value at {@code key} and returns a parsed Component
     * All keys in the {@code replacements} param will be replaced with their value.
     *
     * @param key          path to value
     * @param replacements a map where the key is a placeholder, and the value is what to replace that placeholder with
     * @return Component
     */
    public @NonNull Component c(final @NonNull String key, final @NonNull Map<String, String> replacements) {
        @Nullable String value;

        if (root != null) {
            value = root.node(key.split("\\.")).getString();
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

        return MiniMessage.miniMessage().deserialize(value);
    }

    /**
     * Formats a {@code double} value to currency.
     *
     * @param amount Amount to format.
     * @return Formatted String.
     */
    public static @NonNull String formatCurrency(final double amount) {
        return NumberFormat.getCurrencyInstance().format(amount);
    }

}
