package sh.kaden.crash.command;

import cloud.commandframework.Command;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.Inject;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.Nullable;
import sh.kaden.crash.config.Config;
import sh.kaden.crash.config.Lang;
import sh.kaden.crash.exception.NotEnoughBalanceException;
import sh.kaden.crash.game.crash.CrashProvider;
import sh.kaden.crash.game.crash.CrashManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CrashCommand implements BasicCommand {

    private final @NonNull CrashProvider crashProvider;
    private final @NonNull CrashManager crashManager;
    private final @NonNull List<String> commandCompletions;
    private final @NonNull Lang lang;
    private final @NonNull Config config;

    public CrashCommand(
            final @NonNull CrashProvider crashProvider,
            final @NonNull CrashManager crashManager,
            final @NonNull Lang lang,
            final @NonNull JavaPlugin plugin,
            final @NonNull Config config
    ) {
        this.crashManager = crashManager;
        this.crashProvider = crashProvider;
        this.lang = lang;
        this.config = config;
    }

    /**
     * Handles the /crash main command.
     *
     * @param context command context.
     */
    private void handleCrash(final @NonNull CommandContext<CommandSender> context) {
        final @NonNull CommandSender sender = context.getSender();

        if (!(sender instanceof Player)) {
            return;
        }

        final @NonNull Player player = (Player) sender;

        final @NonNull Optional<String> argumentOptional = context.getOptional("argument");

        if (argumentOptional.isEmpty()) {
            this.crashManager.getMenuManager().showGameMenu((Player) sender);
            return;
        }

        final @NonNull String argument = argumentOptional.get();

        if (argument.equals("history")) {
            // open history menu
            return;
        }

        try {
            final long bet = Long.parseLong(argument);

            if (bet > config.getMaxBet()) {
                this.audiences.player(player).sendMessage(lang.c("over-max-bet"));
                return;
            }

            if (this.crashManager.getBetManager().didBet(player)) {
                this.audiences.player(player).sendMessage(lang.c("already-bet"));
                return;
            }

            this.crashManager.getBetManager().placeBet(player, bet);
        } catch (final NumberFormatException ex) {
            this.audiences.player(player).sendMessage(lang.c("not-a-number"));
        } catch (final NotEnoughBalanceException ex) {
            this.audiences.player(player).sendMessage(lang.c("not-enough-balance"));
        }

    }


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return BasicCommand.super.canUse(sender);
    }

    @Override
    public @Nullable String permission() {
        return BasicCommand.super.permission();
    }
}
