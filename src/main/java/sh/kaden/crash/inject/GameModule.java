package sh.kaden.crash.inject;

import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import sh.kaden.crash.config.Config;
import sh.kaden.crash.config.Lang;
import sh.kaden.crash.config.MenuConfig;
import sh.kaden.crash.game.crash.BetManager;
import sh.kaden.crash.game.crash.CrashProvider;
import sh.kaden.crash.game.crash.CrashManager;
import sh.kaden.crash.menu.MenuManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides objects related to the crash game.
 */
public class GameModule extends AbstractModule {

    /**
     * @return the {@link CrashProvider}.
     */
    @Provides
    @Singleton
    public @NonNull CrashProvider provideCrashProvider() {
        return new CrashProvider();
    }

    /**
     * @param plugin         JavaPlugin reference.
     * @param crashProvider  CrashProvider reference ({@link this#provideCrashProvider()}.
     * @param commandManager PaperCommandManager reference.
     * @param config         Config reference.
     * @param lang           Lang reference.
     * @return the {@link CrashManager}.
     */
    @Provides
    @Singleton
    public @NonNull CrashManager provideGameManager(
            final @NonNull JavaPlugin plugin,
            final @NonNull CrashProvider crashProvider,
            final @NonNull PaperCommandManager<CommandSender> commandManager,
            final @NonNull Config config,
            final @NonNull Lang lang,
            final @NonNull MenuConfig menuConfig
    ) {
        return new CrashManager(plugin, crashProvider, commandManager, config, lang, menuConfig);
    }

    /**
     * @param crashManager GameManager reference.
     * @return {@link MenuManager}.
     */
    @Provides
    @Singleton
    public @NonNull MenuManager provideMenuManager(final @NonNull CrashManager crashManager) {
        return crashManager.getMenuManager();
    }

    /**
     * @param crashManager GameManager reference.
     * @return {@link BetManager}.
     */
    @Provides
    @Singleton
    public @NonNull BetManager provideBetManager(final @NonNull CrashManager crashManager) {
        return crashManager.getBetManager();
    }


}
