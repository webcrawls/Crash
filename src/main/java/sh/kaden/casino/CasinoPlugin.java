package sh.kaden.casino;

import com.google.inject.Guice;
import com.google.inject.Injector;
import sh.kaden.casino.command.CasinoCommand;
import sh.kaden.casino.game.GameManager;
import sh.kaden.casino.inject.CommandModule;
import sh.kaden.casino.inject.EconomyModule;
import sh.kaden.casino.inject.GameModule;
import sh.kaden.casino.inject.PluginModule;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The Casino plugin main class.
 */
public class CasinoPlugin extends JavaPlugin {

    /**
     * How many times Minecraft should tick, per second.
     */
    public static final double MINECRAFT_TICKS_PER_SECOND = 20;

    /**
     * If true, this server isn't running Paper.
     * If false, this server is running Paper or a fork of Paper.
     */
    private static boolean DEPRECATED = false;

    /**
     * The warning to display when {@link this#isDeprecated()} returns {@code true}.
     */
    private static final @NonNull String[] DEPRECATED_WARNING = new String[]{
            "This server is running Spigot or CraftBukkit!",
            "BlueCasino supports these server versions, but support",
            "may be removed in the future.",
            "",
            "These versions may contain unpatched security exploits",
            "provide worse performance, and other issues.",
            "",
            "It is recommended to move to a modern fork such as Paper. (https://papermc.io/)"
    };

    /**
     * Constructs the injector and initializes some stuff.
     */
    @Override
    public void onEnable() {
        // TODO add feature check system
        try {
            this.getServer().spigot().getPaperConfig();
            DEPRECATED = false;
        } catch (final NoSuchMethodError ex) {
            DEPRECATED = true;
        }

        if (DEPRECATED) {
            for (final @NonNull String message : DEPRECATED_WARNING) {
                this.getLogger().warning(message);
            }
        }

        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new RuntimeException("The Vault plugin is not installed. Please install it!");
        }

        final @NonNull Injector injector = Guice.createInjector(
                new PluginModule(this),
                new CommandModule(this),
                new GameModule(),
                new EconomyModule()
        );

        injector.getInstance(GameManager.class);
        injector.getInstance(CasinoCommand.class);
    }

    /**
     * @return {@code true} if this server is running Spigot or CraftBukkit. If {@code false}, this server is likely
     * running Paper, or a fork of Paper.
     */
    public static boolean isDeprecated() {
        return DEPRECATED;
    }
}
