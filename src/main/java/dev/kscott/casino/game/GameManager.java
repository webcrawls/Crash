package dev.kscott.casino.game;

import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages {@link Game}s and their interactions.
 */
public class GameManager {

    /**
     * A Map of {@link GameType} to {@link Game}.
     */
    private final @NonNull Map<GameType, Game> gameMap;

    /**
     * {@link JavaPlugin} reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * Maps a {@link TickingGame} to it's {@link BukkitRunnable}.
     */
    private final Map<TickingGame, BukkitRunnable> tickMap;

    /**
     * Constructs {@link GameManager}.
     *
     * @param plugin {@link JavaPlugin} reference.
     */
    @Inject
    public GameManager(final @NonNull JavaPlugin plugin) {
        this.gameMap = new HashMap<>();
        this.plugin = plugin;
        this.tickMap = new HashMap<>();
    }

    /**
     * Registers a {@link Game} and runs it's setup method.
     * <p>
     * If {@code game} is a {@link TickingGame}, the runnable code will be initialized.
     *
     * @param game the {@link Game} to register.
     */
    public void registerGame(final @NonNull Game game) {
        this.gameMap.put(game.getGameType(), game);
        game.setup();

        if (game instanceof TickingGame) {
            final @NonNull TickingGame tickingGame = (TickingGame) game;

            final int tickTime = tickingGame.getTickSpeed();

            final @NonNull BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    tickingGame.runGameTick();
                }
            };

            this.tickMap.put(tickingGame, runnable);

            tickingGame.startGame();
            runnable.runTaskTimer(plugin, 0, tickTime);
        }
    }

    /**
     * Returns a {@link Game} associated with {@code gameType}.
     *
     * @param gameType {@link GameType} of the {@link Game}.
     * @return a {@link Game} associated with {@code gameType}. Will be null if no {@link Game} was registered.
     */
    public @Nullable Game getGame(final @NonNull GameType gameType) {
        return this.gameMap.get(gameType);
    }

}
