package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface IKeyReleasedEvent extends IEventConsumableRegistrable1<IKeyReleasedEvent.Data> {
    record Data(int keyCode, int scanCode, int modifiers) {}
}
