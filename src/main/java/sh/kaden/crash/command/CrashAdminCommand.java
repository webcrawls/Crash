package sh.kaden.crash.command;

import cloud.commandframework.Command;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import sh.kaden.crash.game.crash.CrashProvider;
import sh.kaden.crash.game.crash.CrashManager;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CrashAdminCommand {

    /**
     * PaperCommandManager reference.
     */
    private final @NonNull PaperCommandManager<CommandSender> commandManager;

    /**
     * CrashProvider reference.
     */
    private final @NonNull CrashProvider crashProvider;

    /**
     * GameManager reference.
     */
    private final @NonNull CrashManager crashManager;

    /**
     * Constructs CrashCommand.
     *
     * @param commandManager PaperCommandManager reference.
     */
    @Inject
    public CrashAdminCommand(
            final @NonNull PaperCommandManager<CommandSender> commandManager,
            final @NonNull CrashProvider crashProvider,
            final @NonNull CrashManager crashManager
    ) {
        this.commandManager = commandManager;
        this.crashManager = crashManager;
        this.crashProvider = crashProvider;

        final Command.Builder<CommandSender> builder = this.commandManager.commandBuilder("crashadmin", "ca");

        this.commandManager.command(
                builder.literal("start")
                        .handler(this::handleStart)
                        .permission("crash.admin")
        );
    }

    /**
     * Handles the /crash main command.
     *
     * @param context command context.
     */
    private void handleStart(final @NonNull CommandContext<CommandSender> context) {
        this.crashManager.startGame();
    }


}
