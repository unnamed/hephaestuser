package team.unnamed.hephaestuser.command.integration;

import me.fixeddev.commandflow.annotated.part.AbstractModule;
import org.jetbrains.annotations.NotNull;
import team.unnamed.hephaestus.Model;
import team.unnamed.hephaestuser.Hephaestuser;

import static java.util.Objects.requireNonNull;

public final class CommandModuleImpl extends AbstractModule {
    private final Hephaestuser hephaestuser;

    public CommandModuleImpl(final @NotNull Hephaestuser hephaestuser) {
        this.hephaestuser = requireNonNull(hephaestuser, "hephaestuser");
    }

    @Override
    public void configure() {
        bindFactory(Model.class, new ModelArgumentFactory(hephaestuser.registry()));
    }
}
