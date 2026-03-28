package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IMouseScrolledEvent extends IEventConsumableRegistrable1<IMouseScrolledEvent.Data> {
    record Data(double mouseX, double mouseY, double scrollX, double scrollY) {}
}
