package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IKeyPressedEvent extends IEventConsumableRegistrable1<IKeyPressedEvent.Data> {
    record Data(int keyCode, int scanCode, int modifiers) {}
}
