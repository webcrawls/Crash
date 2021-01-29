package dev.kscott.casino;

import org.bukkit.plugin.java.JavaPlugin;

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
     * Constructs the injector and initializes some stuff.
     */
    @Override
    public void onEnable() {
        DEPRECATED = this.getServer().getVersion().contains("Spigot") || this.getServer().getVersion().contains("CraftBukkit");

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
