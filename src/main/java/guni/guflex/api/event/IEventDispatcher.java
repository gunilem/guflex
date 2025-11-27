package guni.guflex.api.event;

import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.event.widgetEvents.*;

public interface IEventDispatcher {
    boolean handleMouseClickedEvent(IMouseClickedEvent.Data event, boolean consumed);
    boolean handleMouseReleasedEvent(IMouseReleasedEvent.Data event, boolean consumed);
    boolean handleMouseScrolledEvent(IMouseScrolledEvent.Data event, boolean consumed);
    boolean handleMouseDraggedEvent(IMouseDraggedEvent.Data event, boolean consumed);
    void handleMouseMovedEvent(IMouseMovedEvent.Data event);

    boolean handleKeyPressedEvent(IKeyPressedEvent.Data event, boolean consumed);
    boolean handleKeyReleasedEvent(IKeyReleasedEvent.Data event, boolean consumed);
    boolean handleCharTypedEvent(ICharTypedEvent.Data event, boolean consumed);

    void handleRenderEvent(IRenderEvent.Data event);
    void handleRenderBackgroundEvent(IRenderBackgroundEvent.Data event);
    void handleRenderForegroundEvent(IRenderForegroundEvent.Data event);
    boolean handleRenderTooltipsEvent(IRenderTooltipsEvent.Data event);
    boolean handleRenderDebugEvent(IRenderDebugEvent.Data event);

    void handleShownEvent();
    void handleHiddenEvent();

    void handleAddedEvent();
    void handleRemovedEvent();

    void handleChildAddedEvent(IWidgetChildAddedEvent.Data event);
    void handleChildRemovedEvent(IWidgetChildRemovedEvent.Data event);

    void handleMeasuredEvent();
    void handlePositionedEvent();
    void handleUpdatedEvent();
}
