package sh.kaden.casino.inject;

import com.google.inject.AbstractModule;
import sh.kaden.casino.CasinoPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The Guice module which provides Plugin-related objects and the {@link BukkitAudiences} instance.
 */
public class PluginModule extends AbstractModule {

    /**
     * {@link CasinoPlugin} reference.
     */
    private final @NonNull CasinoPlugin plugin;

    /**
     * {@link BukkitAudiences} instance.
     */
    private final @NonNull BukkitAudiences bukkitAudiences;

    /**
     * Constructs the {@link PluginModule}.
     *
     * @param plugin {@link CasinoPlugin} reference.
     */
    public PluginModule(final @NonNull CasinoPlugin plugin) {
        this.plugin = plugin;
        this.bukkitAudiences = BukkitAudiences.create(plugin);
    }

    /**
     * Configures this {@link PluginModule}.
     */
    @Override
    public void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(JavaPlugin.class).toInstance(this.plugin);
        this.bind(CasinoPlugin.class).toInstance(this.plugin);
        this.bind(BukkitAudiences.class).toInstance(this.bukkitAudiences);
    }

}
