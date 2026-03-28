package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IMouseReleasedEvent extends IEventConsumableRegistrable1<IMouseReleasedEvent.Data> {
    record Data(int button, double mouseX, double mouseY) {}
}
