package guni.guflex.api.event.popupEvents;

import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IPopupClosedEvent {
    record Data(IFlexWidget popup) {}
    boolean onEvent(Data data);
}
