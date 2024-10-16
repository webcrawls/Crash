package sh.kaden.casino.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import sh.kaden.casino.config.CasinoConfig;
import sh.kaden.casino.game.GameManager;
import sh.kaden.casino.menu.MenuManager;

/**
 * Provides the {@link GameManager}.
 */
public class GameModule extends AbstractModule {

    /**
     * Configures {@link GameModule} to provide {@link GameManager}.
     */
    @Override
    public void configure() {
        this.bind(GameManager.class).in(Singleton.class);
        this.bind(MenuManager.class).in(Singleton.class);
        this.bind(CasinoConfig.class).in(Singleton.class);
    }

}
