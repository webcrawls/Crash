package dev.kscott.crash.inject;

import com.google.inject.AbstractModule;
import dev.kscott.casino.CasinoPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides JavaPlugin and CrashPlugin.
 */
public class PluginModule extends AbstractModule {

    /**
     * CrashPlugin reference.
     */
    private final @NonNull CasinoPlugin plugin;

    /**
     * Constructs PluginModule.
     *
     * @param plugin CrashPlugin reference.
     */
    public PluginModule(final @NonNull CasinoPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Configures the {@link PluginModule} to bind {@link Plugin}, {@link JavaPlugin}, and {@link CasinoPlugin} to {@link this#plugin)}.
     */
    @Override
    public void configure() {
        this.bind(Plugin.class).toInstance(plugin);
        this.bind(JavaPlugin.class).toInstance(plugin);
        this.bind(CasinoPlugin.class).toInstance(plugin);
    }

}
