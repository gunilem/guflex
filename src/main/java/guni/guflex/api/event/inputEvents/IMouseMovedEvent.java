package guni.guflex.api.event.inputEvents;

public interface IMouseMovedEvent {
    record Data(double mouseX, double mouseY) {}
    boolean onEvent(Data data);
}
