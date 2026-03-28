package guni.guflex.api.event.widgetEvents;

import guni.guflex.api.event.register.IEventRegistrable1;
import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IWidgetChildRemovedEvent extends IEventRegistrable1<IWidgetChildRemovedEvent.Data> {
    record Data(IFlexWidget widget) {}
}
