package guni.guflex.api.event.inputEvents;

public interface IMouseDraggedEvent {
    record Data(int button, double mouseX, double mouseY, double dragX, double dragY) {}
    boolean onEvent(Data data);
}
