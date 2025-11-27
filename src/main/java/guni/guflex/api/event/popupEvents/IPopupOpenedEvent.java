package guni.guflex.api.event.popupEvents;

import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IPopupOpenedEvent {
    record Data(IFlexWidget popup) {}
    boolean onEvent(Data data);
}
