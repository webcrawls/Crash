package dev.kscott.casino.config;

import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Holds language data for the main casino stuff.
 */
public class CasinoLang extends LangConfig {

    /**
     * Constructs the lang.
     *
     * @param plugin {@link JavaPlugin} reference
     */
    @Inject
    public CasinoLang(final @NonNull JavaPlugin plugin) {
        super(plugin, "lang.conf");
    }
}
