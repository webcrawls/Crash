package dev.kscott.casino;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.crash.Metrics;
import dev.kscott.crash.command.CrashAdminCommand;
import dev.kscott.crash.command.CrashCommand;
import dev.kscott.crash.inject.CommandModule;
import dev.kscott.crash.inject.ConfigModule;
import dev.kscott.crash.inject.GameModule;
import dev.kscott.crash.inject.PluginModule;
import dev.kscott.crash.listeners.InventoryCloseListener;
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
    public static boolean IS_DEPRECATED = false;

    /**
     * Constructs the injector and initializes some stuff.
     */
    @Override
    public void onEnable() {
        IS_DEPRECATED = this.getServer().getVersion().contains("Spigot") || this.getServer().getVersion().contains("Craftbukkit");
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new RuntimeException("The Vault plugin is not installed. Please install it!");
        }
    }


}
