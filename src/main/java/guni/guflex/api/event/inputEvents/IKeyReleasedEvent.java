package guni.guflex.api.event.inputEvents;

public interface IKeyReleasedEvent {
    record Data(int keyCode, int scanCode, int modifiers) {}
    boolean onEvent(Data data);
}
