package team.unnamed.hephaestuser;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import team.unnamed.hephaestus.bukkit.BukkitModelEngine;
import team.unnamed.hephaestus.bukkit.v1_20_R3.BukkitModelEngine_v1_20_R3;
import team.unnamed.hephaestuser.registry.ModelRegistry;
import team.unnamed.hephaestuser.registry.ModelViewPersistenceHandlerImpl;

import static java.util.Objects.requireNonNull;

public final class Hephaestuser {
    private final Plugin plugin;
    private final ModelRegistry registry;
    private final BukkitModelEngine engine;

    public Hephaestuser(final @NotNull Plugin plugin) {
        this.plugin = requireNonNull(plugin, "plugin");
        this.registry = new ModelRegistry();
        this.engine = BukkitModelEngine_v1_20_R3.create(plugin, new ModelViewPersistenceHandlerImpl(registry));
    }

    public @NotNull Plugin plugin() {
        return plugin;
    }

    public @NotNull ModelRegistry registry() {
        return registry;
    }

    public @NotNull BukkitModelEngine engine() {
        return engine;
    }

    void close() {
        if (engine != null) {
            engine.close();
        }
    }
}
