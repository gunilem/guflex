package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IMouseDraggedEvent extends IEventConsumableRegistrable1<IMouseDraggedEvent.Data> {
    record Data(int button, double mouseX, double mouseY, double dragX, double dragY) {}
}
