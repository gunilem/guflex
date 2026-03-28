package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IMouseClickedEvent extends IEventConsumableRegistrable1<IMouseClickedEvent.Data> {
    record Data(int button, double mouseX, double mouseY) {}
}
