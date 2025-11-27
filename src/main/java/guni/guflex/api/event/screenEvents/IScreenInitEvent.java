package guni.guflex.api.event.screenEvents;

import net.minecraft.client.gui.screens.Screen;

public interface IScreenInitEvent {
    record Data(Screen screen) {}
    void onEvent(Data data);
}
