package guni.guflex;

import guni.guflex.core.registers.Internals;
import guni.guflex.core.style.RegisterStyle;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class GuFlexClient {
    public GuFlexClient(IEventBus modEventBus) {
        modEventBus.addListener(this::onRegisterReloadListenerEvent);
        RegisterStyle.registerStyles();
    }

    private void onRegisterReloadListenerEvent(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(Internals.getTextures().getAtlas());
    }
}
