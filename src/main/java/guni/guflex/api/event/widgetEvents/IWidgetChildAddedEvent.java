package guni.guflex.api.event.widgetEvents;

import guni.guflex.api.event.register.IEventRegistrable1;
import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IWidgetChildAddedEvent extends IEventRegistrable1<IWidgetChildAddedEvent.Data> {
    record Data(IFlexWidget widget) {}
}
