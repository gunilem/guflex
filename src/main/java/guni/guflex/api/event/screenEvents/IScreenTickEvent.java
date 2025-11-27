package guni.guflex.api.event.screenEvents;

public interface IScreenTickEvent {
    record Data(int tickSinceScreenOpened) {}
    void onEvent(Data data);
}
