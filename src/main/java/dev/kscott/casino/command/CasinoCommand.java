package dev.kscott.casino.command;

import cloud.commandframework.Command;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import dev.kscott.casino.config.CasinoConfig;
import dev.kscott.casino.menu.MenuManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Registers commands for BlueCasino.
 */
public class CasinoCommand {

    public static final @NonNull String P_CRASH_COMMAND_USE = "casino.crash.command.use";

    /**
     * {@link PaperCommandManager} reference.
     */
    private final @NonNull PaperCommandManager<CommandSender> commandManager;

    /**
     * {@link CasinoConfig} reference.
     */
    private final @NonNull CasinoConfig config;

    /**
     * {@link BukkitAudiences} reference.
     */
    private final @NonNull BukkitAudiences audiences;

    /**
     * {@link MenuManager} reference.
     */
    private final @NonNull MenuManager menuManager;

    /**
     * Constructs {@link CasinoCommand}.
     *
     * @param commandManager {@link PaperCommandManager} reference.
     * @param config         {@link CasinoConfig} reference.
     * @param audiences      {@link BukkitAudiences} reference.
     * @param menuManager    {@link MenuManager} reference.
     */
    @Inject
    public CasinoCommand(
            final @NonNull PaperCommandManager<CommandSender> commandManager,
            final @NonNull CasinoConfig config,
            final @NonNull BukkitAudiences audiences,
            final @NonNull MenuManager menuManager
    ) {
        this.commandManager = commandManager;
        this.audiences = audiences;
        this.config = config;
        this.menuManager = menuManager;
    }

    private void registerCommands() {
        // Crash
        final Command.Builder<CommandSender> crashBuilder = this.commandManager.commandBuilder("crash");

        this.commandManager.command(
                crashBuilder.handler(this::handleCrashCommand)
                        .permission(P_CRASH_COMMAND_USE)
        );

    }

    private void handleCrashCommand(final @NonNull CommandContext<CommandSender> context) {

    }

}
