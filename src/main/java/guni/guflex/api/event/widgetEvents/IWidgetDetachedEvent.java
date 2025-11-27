package guni.guflex.api.event.widgetEvents;

import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IWidgetDetachedEvent {
    record Data(IFlexWidget widget) {}
    void onEvent(Data data);
}
