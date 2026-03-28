package guni.guflex.api.event.inputEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;

public interface ICharTypedEvent extends IEventConsumableRegistrable1<ICharTypedEvent.Data> {
    record Data(char codePoint, int modifiers) {}
}
