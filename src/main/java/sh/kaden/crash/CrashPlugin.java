package sh.kaden.crash;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The CrashPlugin main class.
 */
public final class CrashPlugin extends JavaPlugin {

    /**
     * Constructs the injector and initializes some stuff.
     */
    @Override
    public void onEnable() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new RuntimeException("The Vault plugin is not installed. Please install it!");
        }
    }
}
