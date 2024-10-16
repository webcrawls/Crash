package sh.kaden.casino.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores data to use for the menu icon.
 */
public class MenuIconData {

    /**
     * A {@link Set} of {@link ItemFlag}s to be put onto this icon.
     */
    private final @Nullable Set<ItemFlag> itemFlags;

    /**
     * The {@link Component} to set as this icon's display name.
     */
    private final @Nullable Component name;

    /**
     * The lore of this icon.
     */
    private final @Nullable List<Component> lore;

    /**
     * The {@link Material} of this icon.
     */
    private final @Nullable Material material;

    /**
     * Constructs the {@link MenuIconData}.
     *
     * @param material {@link Material} of this icon.
     * @param name     name of this icon.
     * @param lore     lore of this icon.
     * @param flags    flags of this icon.
     */
    public MenuIconData(
            final @NonNull Material material,
            final @Nullable Component name,
            final @Nullable List<Component> lore,
            final @Nullable Set<ItemFlag> flags
    ) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.itemFlags = flags;
    }

    /**
     * Constructs a {@link MenuIconData}.
     */
    static class Builder {

        /**
         * The {@link Material} of the {@link MenuIconData}.
         */
        private final @NonNull Material material;

        /**
         * The name of the {@link MenuIconData}.
         */
        private @Nullable Component name;

        /**
         * The lore of the {@link MenuIconData}.
         */
        private @Nullable List<Component> lore;

        /**
         * The {@link ItemFlag}s of the {@link MenuIconData}.
         */
        private @Nullable Set<ItemFlag> flags;

        /**
         * Constructs a new builder.
         *
         * @param material {@link Material} of the {@link MenuIconData}.
         */
        Builder(final @NonNull Material material) {
            this.material = material;
        }

        /**
         * Sets the name of this builder.
         *
         * @param name name.
         * @return this builder.
         */
        public @NonNull Builder withName(final @NonNull Component name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the lore of this builder.
         *
         * @param lore lore.
         * @return this builder.
         */
        public @NonNull Builder withLore(final @NonNull List<Component> lore) {
            this.lore = lore;
            return this;
        }

        /**
         * Sets the lore of this builder.
         *
         * @param lore lore.
         * @return this builder.
         */
        public @NonNull Builder withLore(final @NonNull Component... lore) {
            this.lore = Arrays.asList(lore);
            return this;
        }

        /**
         * Sets the flags of this builder.
         *
         * @param flags flags.
         * @return this builder.
         */
        public @NonNull Builder withFlags(final @NonNull List<ItemFlag> flags) {
            this.flags = new HashSet<>(flags);
            return this;
        }

        /**
         * Sets the flags of this builder.
         *
         * @param flags flags.
         * @return this builder.
         */
        public @NonNull Builder withFlags(final @NonNull ItemFlag... flags) {
            return withFlags(Arrays.asList(flags));
        }

        /**
         * Constructs the {@link MenuIconData}.
         *
         * @return a new {@link MenuIconData}.
         */
        public @NonNull MenuIconData build() {
            return new MenuIconData(material, name, lore, flags);
        }

    }

}
