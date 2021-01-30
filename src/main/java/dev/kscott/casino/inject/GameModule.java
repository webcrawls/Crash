package dev.kscott.casino.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import dev.kscott.casino.game.GameManager;
import dev.kscott.casino.menu.MenuManager;

/**
 * Provides the {@link dev.kscott.casino.game.GameManager}.
 */
public class GameModule extends AbstractModule {

    /**
     * Configures {@link GameModule} to provide {@link GameManager}.
     */
    @Override
    public void configure() {
        this.bind(GameManager.class).in(Singleton.class);
        this.bind(MenuManager.class).in(Singleton.class);
    }

}
