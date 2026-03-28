package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IMouseMovedEvent extends IEventConsumableRegistrable1<IMouseMovedEvent.Data> {
    record Data(double mouseX, double mouseY) {}
}
