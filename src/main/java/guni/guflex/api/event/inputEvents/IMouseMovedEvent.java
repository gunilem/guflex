package guni.guflex.api.event.inputEvents;

public interface IMouseMovedEvent {
    record Data(double mouseX, double mouseY) {}
    void onEvent(Data data);
}
