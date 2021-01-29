package dev.kscott.casino.inject;

import cloud.commandframework.CommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.AbstractModule;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

/**
 * Provides cloud-related objects.
 */
public class CommandModule extends AbstractModule {

    private final @NonNull PaperCommandManager<CommandSender> commandManager;

    /**
     * Constructs {@link CommandModule}.
     *
     * @param plugin {@link JavaPlugin} reference.
     */
    public CommandModule(
            final @NonNull JavaPlugin plugin
    ) {
        try {
            final @NonNull Function<CommandSender, CommandSender> mapper = Function.identity();

            commandManager = new PaperCommandManager<>(
                    plugin,
                    // TODO this is sync, make it not sync thanks broccypoo
                    AsynchronousCommandExecutionCoordinator
                            .<CommandSender>newBuilder()
                            .withAsynchronousParsing()
                            .build(),
                    mapper,
                    mapper
            );

            if (commandManager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
                commandManager.registerAsynchronousCompletions();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize the CommandManager.");
        }
    }

    /**
     * Configures {@link CommandModule} to bind the {@link PaperCommandManager} instance.
     */
    @Override
    public void configure() {
        this.bind(CommandManager.class).toInstance(this.commandManager);
        this.bind(PaperCommandManager.class).toInstance(this.commandManager);
    }

}
