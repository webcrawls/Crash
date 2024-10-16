package sh.kaden.crash;

import com.google.inject.Guice;
import com.google.inject.Injector;
import sh.kaden.crash.command.CrashAdminCommand;
import sh.kaden.crash.command.CrashCommand;
import sh.kaden.crash.inject.CommandModule;
import sh.kaden.crash.inject.ConfigModule;
import sh.kaden.crash.inject.GameModule;
import sh.kaden.crash.listeners.InventoryCloseListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The CrashPlugin main class.
 */
public final class CrashPlugin extends JavaPlugin {

    /**
     * If true, this server is Spigot or CB.
     * If false, it's something else.
     */
    public static boolean IS_DEPRECATED;;

    /**
     * Constructs the injector and initializes some stuff.
     */
    @Override
    public void onEnable() {
        IS_DEPRECATED = this.getServer().getVersion().contains("Spigot") || this.getServer().getVersion().contains("Craftbukkit");
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new RuntimeException("The Vault plugin is not installed. Please install it!");
        }

        final @NonNull Injector injector = Guice.createInjector(
                //new PluginModule(this),
                new CommandModule(this),
                new ConfigModule(),
                new GameModule()
        );

        injector.getInstance(CrashCommand.class);
        injector.getInstance(CrashAdminCommand.class);

        this.getServer().getPluginManager().registerEvents(injector.getInstance(InventoryCloseListener.class), this);

        new Metrics(this, 10168);
    }
}
