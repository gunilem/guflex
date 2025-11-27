package guni.guflex.api.event.inputEvents;

public interface IMouseScrolledEvent {
    record Data(double mouseX, double mouseY, double scrollX, double scrollY) {}
    boolean onEvent(Data data);
}
