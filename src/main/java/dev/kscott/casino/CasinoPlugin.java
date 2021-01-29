package dev.kscott.casino;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.crash.inject.CommandModule;
import dev.kscott.crash.inject.PluginModule;
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
    private static final @NonNull String[] DEPRECATED_WARNING = new String[]{
            "This server is running Spigot or CraftBukkit!",
            "BlueCasino supports these server versions, but support",
            "may be removed in the future.",
            "",
            "These versions may contain unpatched security exploits",
            "provide worse performance, and other issues.",
            "",
            "It is recommended to move to a modern fork such as Paper. (https://papermc.io/)"
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

        final @NonNull Injector injector = Guice.createInjector(
                new PluginModule(this),
                new CommandModule(this)
        );
    }

    /**
     * @return {@code true} if this server is running Spigot or CraftBukkit. If {@code false}, this server is likely
     * running Paper, or a fork of Paper.
     */
    public static boolean isDeprecated() {
        return DEPRECATED;
    }
}
