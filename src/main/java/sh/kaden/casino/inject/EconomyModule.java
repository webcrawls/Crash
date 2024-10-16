package sh.kaden.casino.inject;

import com.google.inject.AbstractModule;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Provides the Vault Economy API.
 */
public class EconomyModule extends AbstractModule {

    /**
     * Configures {@link EconomyModule} to provide the Vault {@link Economy} object.
     * <p>
     * Will throw a RuntimeException if the API is not present. This will occur in cases where no economy plugin
     * is present.
     */
    @Override
    public void configure() {
        final @Nullable RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null || rsp.getProvider() == null) {
            throw new RuntimeException("Vault economy API not found! Please ensure you have a valid economy plugin, such as EssentialsX.");
        }

        this.bind(Economy.class).toInstance(rsp.getProvider());
    }

}
