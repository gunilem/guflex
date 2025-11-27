package guni.guflex.api.event.inputEvents;

public interface ICharTypedEvent {
    record Data(char codePoint, int modifiers) {}
    boolean onEvent(Data data);
}
