package guni.guflex.api.event.screenEvents;

import guni.guflex.api.event.register.IEventRegistrable1;
import net.minecraft.client.gui.screens.Screen;

public interface IScreenInitEvent extends IEventRegistrable1<IScreenInitEvent.Data> {
    record Data(Screen screen) {}
}
