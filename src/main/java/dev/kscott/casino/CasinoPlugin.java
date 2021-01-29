package dev.kscott.casino;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The Casino plugin main class.
 */
public class CasinoPlugin extends JavaPlugin {

    /**
     * If true, this server is Spigot or CB.
     * If false, it's something else.
     */
    private static boolean DEPRECATED = false;

    /**
     * The warning to display when {@link this#isDeprecated()} returns {@code true}.
     */
    private static final String[] DEPRECATED_WARNING = new String[]{
            "This server is running Spigot or CraftBukkit!",
            "BlueCasino supports these server versions, but",
            "these server versions are unoptimized and may contain",
            "unpatched security exploits.",
            "",
            "Support may be removed in the future, it is recommended to",
            "switch to a modern server such as Paper (https://papermc.io"
    };

    /**
     * Constructs the injector and initializes some stuff.
     */
    @Override
    public void onEnable() {
        DEPRECATED = this.getServer().getVersion().contains("Spigot") || this.getServer().getVersion().contains("CraftBukkit");

        if (DEPRECATED) {
            for (final @NonNull String message : DEPRECATED_WARNING) {
                this.getLogger().warning(message);
            }
        }

        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new RuntimeException("The Vault plugin is not installed. Please install it!");
        }
    }

    /**
     * @return {@code true} if this server is running Spigot or CraftBukkit. If {@code false}, this server is likely
     * running Paper, or a fork of Paper.
     */
    public static boolean isDeprecated() {
        return DEPRECATED;
    }
}
