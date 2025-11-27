package guni.guflex;

import net.neoforged.bus.api.IEventBus;

public class GuFlexSafeClient {

    private IEventBus modEventBus;

    public GuFlexSafeClient(IEventBus modEventBus){
        this.modEventBus = modEventBus;
    }

    public void registerClient(){
        new GuFlexClient(modEventBus);
    }
}
