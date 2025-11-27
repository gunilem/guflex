package guni.guflex.api.event.inputEvents;

public interface IKeyReleasedConsumedEvent {
    void onEvent(IKeyReleasedEvent.Data data);
}
