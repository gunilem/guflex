package guni.guflex.api.event.inputEvents;

public interface IMouseClickedEvent {
    record Data(int button, double mouseX, double mouseY) {}
    boolean onEvent(Data data);
}
