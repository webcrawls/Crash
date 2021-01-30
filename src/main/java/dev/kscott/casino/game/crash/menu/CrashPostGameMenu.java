package dev.kscott.casino.game.crash.menu;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import dev.kscott.casino.game.GameType;
import dev.kscott.casino.game.crash.CrashGame;
import dev.kscott.casino.menu.GameMenu;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The menu to display when {@link CrashGame} is in it's pre-game phase.
 */
public class CrashPostGameMenu extends GameMenu<CrashGame> {

    /**
     * Constructs the CrashPostGameMenu.
     *
     * @param game The {@link CrashGame} reference.
     */
    public CrashPostGameMenu(final @NonNull CrashGame game) {
        super(CrashGame.MT_POST_GAME, GameType.CRASH, game);
    }

    /**
     * Constructs the {@link CrashPostGameMenu}.
     *
     * @param player the {@link Player} intended to view this {@link GameMenu}.
     * @return the constructed {@link GameMenu}.
     */
    @Override
    public Gui constructMenu(final @NonNull Player player) {
        final @NonNull ChestGui gui = new ChestGui(6, "Crash Post Game "+this.game.getPostGameSeconds()+"s");

        return gui;

    }
}
