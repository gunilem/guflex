package guni.guflex.api.event.inputEvents;

public interface IMouseReleasedEvent {
    record Data(int button, double mouseX, double mouseY) {}
    boolean onEvent(Data data);
}
