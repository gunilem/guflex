package guni.guflex.api.event;

import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.event.widgetEvents.*;
import guni.guflex.api.runtime.screen.IFlexScreen;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class FlexWidgetEventHandler {
    protected List<IMouseClickedEvent> mouseClickedEventRegister = new ArrayList<>();
    protected List<IMouseReleasedEvent> mouseReleasedEventRegister = new ArrayList<>();
    protected List<IMouseScrolledEvent> mouseScrolledEventRegister = new ArrayList<>();
    protected List<IMouseDraggedEvent> mouseDraggedEventRegister = new ArrayList<>();
    protected List<IKeyPressedEvent> keyPressedEventRegister = new ArrayList<>();
    protected List<IKeyReleasedEvent> keyReleasedEventRegister = new ArrayList<>();
    protected List<ICharTypedEvent> charTypedEventRegister = new ArrayList<>();

    protected List<IMouseClickedConsumedEvent> mouseClickedConsumedEventRegister = new ArrayList<>();
    protected List<IMouseReleasedConsumedEvent> mouseReleasedConsumedEventRegister = new ArrayList<>();
    protected List<IMouseScrolledConsumedEvent> mouseScrolledConsumedEventRegister = new ArrayList<>();
    protected List<IMouseDraggedConsumedEvent> mouseDraggedConsumedEventRegister = new ArrayList<>();
    protected List<IKeyPressedConsumedEvent> keyPressedConsumedEventRegister = new ArrayList<>();
    protected List<IKeyReleasedConsumedEvent> keyReleasedConsumedEventRegister = new ArrayList<>();
    protected List<ICharTypedConsumedEvent> charTypedConsumedEventRegister = new ArrayList<>();

    protected List<IMouseMovedEvent> mouseMovedEventRegister = new ArrayList<>();

    protected List<IRenderEvent> renderEventRegister = new ArrayList<>();
    protected List<IRenderBackgroundEvent> renderBackgroundEventRegister = new ArrayList<>();
    protected List<IRenderForegroundEvent> renderForegroundEventRegister = new ArrayList<>();
    protected List<IRenderTooltipsEvent> renderTooltipsEventRegister = new ArrayList<>();
    protected List<IRenderDebugEvent> renderDebugEventRegister = new ArrayList<>();

    protected List<IWidgetShownEvent> widgetShownEventRegister = new ArrayList<>();
    protected List<IWidgetHiddenEvent> widgetHiddenEventRegister = new ArrayList<>();
    protected List<IWidgetAddedEvent> widgetAddedEventRegister = new ArrayList<>();
    protected List<IWidgetRemovedEvent> widgetRemovedEventRegister = new ArrayList<>();
    protected List<IWidgetChildAddedEvent> widgetChildAddedEventRegister = new ArrayList<>();
    protected List<IWidgetChildRemovedEvent> widgetChildRemovedEventRegister = new ArrayList<>();
    protected List<IWidgetMeasuredEvent> widgetMeasuredEventRegister = new ArrayList<>();
    protected List<IWidgetPositionedEvent> widgetPositionedEventRegister = new ArrayList<>();
    protected List<IWidgetUpdatedEvent> widgetUpdatedEventRegister = new ArrayList<>();

    //register Mouse Event
    public void registerMouseClickedEvent(IMouseClickedEvent event) { mouseClickedEventRegister.add(event); }
    public void registerMouseReleasedEvent(IMouseReleasedEvent event) { mouseReleasedEventRegister.add(event); }
    public void registerMouseScrolledEvent(IMouseScrolledEvent event) { mouseScrolledEventRegister.add(event); }
    public void registerMouseDraggedEvent(IMouseDraggedEvent event) { mouseDraggedEventRegister.add(event); }
    public void registerKeyPressedEvent(IKeyPressedEvent event) { keyPressedEventRegister.add(event); }
    public void registerKeyReleasedEvent(IKeyReleasedEvent event) { keyReleasedEventRegister.add(event); }
    public void registerCharTypedEvent(ICharTypedEvent event) { charTypedEventRegister.add(event); }

    //unregister Mouse Event
    public void unregisterMouseClickedEvent(IMouseClickedEvent event) { mouseClickedEventRegister.remove(event); }
    public void unregisterMouseReleasedEvent(IMouseReleasedEvent event) { mouseReleasedEventRegister.remove(event); }
    public void unregisterMouseScrolledEvent(IMouseScrolledEvent event) { mouseScrolledEventRegister.remove(event); }
    public void unregisterMouseDraggedEvent(IMouseDraggedEvent event) { mouseDraggedEventRegister.remove(event); }
    public void unregisterKeyPressedEvent(IKeyPressedEvent event) { keyPressedEventRegister.remove(event); }
    public void unregisterKeyReleasedEvent(IKeyReleasedEvent event) { keyReleasedEventRegister.remove(event); }
    public void unregisterCharTypedEvent(ICharTypedEvent event) { charTypedEventRegister.remove(event); }


    //register Mouse Consumed Event
    public void registerMouseClickedConsumedEvent(IMouseClickedConsumedEvent event) { mouseClickedConsumedEventRegister.add(event); }
    public void registerMouseReleasedConsumedEvent(IMouseReleasedConsumedEvent event) { mouseReleasedConsumedEventRegister.add(event); }
    public void registerMouseScrolledConsumedEvent(IMouseScrolledConsumedEvent event) { mouseScrolledConsumedEventRegister.add(event); }
    public void registerMouseDraggedConsumedEvent(IMouseDraggedConsumedEvent event) { mouseDraggedConsumedEventRegister.add(event); }
    public void registerKeyPressedConsumedEvent(IKeyPressedConsumedEvent event) { keyPressedConsumedEventRegister.add(event); }
    public void registerKeyReleasedConsumedEvent(IKeyReleasedConsumedEvent event) { keyReleasedConsumedEventRegister.add(event); }
    public void registerCharTypedConsumedEvent(ICharTypedConsumedEvent event) { charTypedConsumedEventRegister.add(event); }

    //unregister Mouse Consumed Event
    public void unregisterMouseClickedConsumedEvent(IMouseClickedConsumedEvent event) { mouseClickedConsumedEventRegister.remove(event); }
    public void unregisterMouseReleasedConsumedEvent(IMouseReleasedConsumedEvent event) { mouseReleasedConsumedEventRegister.remove(event); }
    public void unregisterMouseScrolledConsumedEvent(IMouseScrolledConsumedEvent event) { mouseScrolledConsumedEventRegister.remove(event); }
    public void unregisterMouseDraggedConsumedEvent(IMouseDraggedConsumedEvent event) { mouseDraggedConsumedEventRegister.remove(event); }
    public void unregisterKeyPressedConsumedEvent(IKeyPressedConsumedEvent event) { keyPressedConsumedEventRegister.remove(event); }
    public void unregisterKeyReleasedConsumedEvent(IKeyReleasedConsumedEvent event) { keyReleasedConsumedEventRegister.remove(event); }
    public void unregisterCharTypedConsumedEvent(ICharTypedConsumedEvent event) { charTypedConsumedEventRegister.remove(event); }

    public void registerMouseMovedEvent(IMouseMovedEvent event) { mouseMovedEventRegister.add(event); }
    public void unregisterMouseMovedEvent(IMouseMovedEvent event) { mouseMovedEventRegister.remove(event); }

    //register render event
    public void registerRenderEvent(IRenderEvent event) { renderEventRegister.add(event); }
    public void registerRenderBackgroundEvent(IRenderBackgroundEvent event) { renderBackgroundEventRegister.add(event); }
    public void registerRenderForegroundEvent(IRenderForegroundEvent event) { renderForegroundEventRegister.add(event); }
    public void registerRenderTooltipsEvent(IRenderTooltipsEvent event) { renderTooltipsEventRegister.add(event); }
    public void registerRenderDebugEvent(IRenderDebugEvent event) { renderDebugEventRegister.add(event); }

    //unregister render event
    public void unregisterRenderEvent(IRenderEvent event) { renderEventRegister.remove(event); }
    public void unregisterRenderBackgroundEvent(IRenderBackgroundEvent event) { renderBackgroundEventRegister.remove(event); }
    public void unregisterRenderForegroundEvent(IRenderForegroundEvent event) { renderForegroundEventRegister.remove(event); }
    public void unregisterRenderTooltipsEvent(IRenderTooltipsEvent event) { renderTooltipsEventRegister.remove(event); }
    public void unregisterRenderDebugEvent(IRenderDebugEvent event) { renderDebugEventRegister.remove(event); }

    //register widget event
    public void registerWidgetShownEvent(IWidgetShownEvent event) { widgetShownEventRegister.add(event); }
    public void registerWidgetHiddenEvent(IWidgetHiddenEvent event) { widgetHiddenEventRegister.add(event); }
    public void registerWidgetAddedEvent(IWidgetAddedEvent event) { widgetAddedEventRegister.add(event); }
    public void registerWidgetRemovedEvent(IWidgetRemovedEvent event) { widgetRemovedEventRegister.add(event); }
    public void registerWidgetChildAddedEvent(IWidgetChildAddedEvent event) { widgetChildAddedEventRegister.add(event); }
    public void registerWidgetChildRemovedEvent(IWidgetChildRemovedEvent event) { widgetChildRemovedEventRegister.add(event); }
    public void registerWidgetMeasuredEvent(IWidgetMeasuredEvent event) { widgetMeasuredEventRegister.add(event); }
    public void registerWidgetPositionedEvent(IWidgetPositionedEvent event) { widgetPositionedEventRegister.add(event); }
    public void registerWidgetUpdatedEvent(IWidgetUpdatedEvent event) { widgetUpdatedEventRegister.add(event); }

    //unregister widget event
    public void unregisterWidgetShownEvent(IWidgetShownEvent event) { widgetShownEventRegister.remove(event); }
    public void unregisterWidgetHiddenEvent(IWidgetHiddenEvent event) { widgetHiddenEventRegister.remove(event); }
    public void unregisterWidgetAddedEvent(IWidgetAddedEvent event) { widgetAddedEventRegister.remove(event); }
    public void unregisterWidgetRemovedEvent(IWidgetRemovedEvent event) { widgetRemovedEventRegister.remove(event); }
    public void unregisterWidgetChildAddedEvent(IWidgetChildAddedEvent event) { widgetChildAddedEventRegister.remove(event); }
    public void unregisterWidgetChildRemovedEvent(IWidgetChildRemovedEvent event) { widgetChildRemovedEventRegister.remove(event); }
    public void unregisterWidgetMeasuredEvent(IWidgetMeasuredEvent event) { widgetMeasuredEventRegister.remove(event); }
    public void unregisterWidgetPositionedEvent(IWidgetPositionedEvent event) { widgetPositionedEventRegister.remove(event); }
    public void unregisterWidgetUpdatedEvent(IWidgetUpdatedEvent event) { widgetUpdatedEventRegister.remove(event); }

    public void registerScreenTickEvent(IScreenTickEvent event) {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            screen.screenEventHandler().register(event);
        }
    }
    public void registerScreenInitEvent(IScreenInitEvent event) {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            screen.screenEventHandler().register(event);
        }
    }

    public void unregisterScreenTickEvent(IScreenTickEvent event) {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            screen.screenEventHandler().unregister(event);
        }
    }
    public void unregisterScreenInitEvent(IScreenInitEvent event) {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            screen.screenEventHandler().unregister(event);
        }
    }

    public boolean invokeMouseClickedEvent(IMouseClickedEvent.Data data){
        boolean consumed = false;
        for (IMouseClickedEvent event : mouseClickedEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeMouseReleasedEvent(IMouseReleasedEvent.Data data){
        boolean consumed = false;
        for (IMouseReleasedEvent event : mouseReleasedEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeMouseScrolledEvent(IMouseScrolledEvent.Data data){
        boolean consumed = false;
        for (IMouseScrolledEvent event : mouseScrolledEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeMouseDraggedEvent(IMouseDraggedEvent.Data data){
        boolean consumed = false;
        for (IMouseDraggedEvent event : mouseDraggedEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeKeyPressedEvent(IKeyPressedEvent.Data data){
        boolean consumed = false;
        for (IKeyPressedEvent event : keyPressedEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeKeyReleasedEvent(IKeyReleasedEvent.Data data){
        boolean consumed = false;
        for (IKeyReleasedEvent event : keyReleasedEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeCharTypedEvent(ICharTypedEvent.Data data){
        boolean consumed = false;
        for (ICharTypedEvent event : charTypedEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }

    public void invokeMouseClickedConsumedEvent(IMouseClickedEvent.Data data){
        for (IMouseClickedConsumedEvent event : mouseClickedConsumedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeMouseReleasedConsumedEvent(IMouseReleasedEvent.Data data){
        for (IMouseReleasedConsumedEvent event : mouseReleasedConsumedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeMouseScrolledConsumedEvent(IMouseScrolledEvent.Data data){
        for (IMouseScrolledConsumedEvent event : mouseScrolledConsumedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeMouseDraggedConsumedEvent(IMouseDraggedEvent.Data data){
        for (IMouseDraggedConsumedEvent event : mouseDraggedConsumedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeKeyPressedConsumedEvent(IKeyPressedEvent.Data data){
        for (IKeyPressedConsumedEvent event : keyPressedConsumedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeKeyReleasedConsumedEvent(IKeyReleasedEvent.Data data){
        for (IKeyReleasedConsumedEvent event : keyReleasedConsumedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeCharTypedConsumedEvent(ICharTypedEvent.Data data){
        for (ICharTypedConsumedEvent event : charTypedConsumedEventRegister){
            event.onEvent(data);
        }
    }

    public void invokeMouseMovedEvent(IMouseMovedEvent.Data data){
        for (IMouseMovedEvent event : mouseMovedEventRegister){
            event.onEvent(data);
        }
    }

    public void invokeRenderEvent(IRenderEvent.Data data){
        for (IRenderEvent event : renderEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeRenderBackgroundEvent(IRenderBackgroundEvent.Data data){
        for (IRenderBackgroundEvent event : renderBackgroundEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeRenderForegroundEvent(IRenderForegroundEvent.Data data){
        for (IRenderForegroundEvent event : renderForegroundEventRegister){
            event.onEvent(data);
        }
    }
    public boolean invokeRenderTooltipsEvent(IRenderTooltipsEvent.Data data){
        boolean consumed = false;
        for (IRenderTooltipsEvent event : renderTooltipsEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }
    public boolean invokeRenderDebugEvent(IRenderDebugEvent.Data data){
        boolean consumed = false;
        for (IRenderDebugEvent event : renderDebugEventRegister){
            if (event.onEvent(data)) consumed = true;
        }
        return consumed;
    }

    public void invokeWidgetShownEvent(){
        for (IWidgetShownEvent event : widgetShownEventRegister){
            event.onEvent();
        }
    }
    public void invokeWidgetHiddenEvent(){
        for (IWidgetHiddenEvent event : widgetHiddenEventRegister){
            event.onEvent();
        }
    }
    public void invokeWidgetAddedEvent(){
        for (IWidgetAddedEvent event : widgetAddedEventRegister){
            event.onEvent();
        }
    }
    public void invokeWidgetRemovedEvent(){
        for (IWidgetRemovedEvent event : widgetRemovedEventRegister){
            event.onEvent();
        }
    }
    public void invokeWidgetChildAddedEvent(IWidgetChildAddedEvent.Data data){
        for (IWidgetChildAddedEvent event : widgetChildAddedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeWidgetChildRemovedEvent(IWidgetChildRemovedEvent.Data data){
        for (IWidgetChildRemovedEvent event : widgetChildRemovedEventRegister){
            event.onEvent(data);
        }
    }
    public void invokeWidgetMeasuredEvent(){
        for (IWidgetMeasuredEvent event : widgetMeasuredEventRegister){
            event.onEvent();
        }
    }
    public void invokeWidgetPositionedEvent(){
        for (IWidgetPositionedEvent event : widgetPositionedEventRegister){
            event.onEvent();
        }
    }
    public void invokeWidgetUpdatedEvent(){
        for (IWidgetUpdatedEvent event : widgetUpdatedEventRegister){
            event.onEvent();
        }
    }


    public void release(){
        mouseClickedEventRegister.clear();
        mouseReleasedEventRegister.clear();
        mouseDraggedEventRegister.clear();
        mouseScrolledEventRegister.clear();
        keyPressedEventRegister.clear();
        keyReleasedEventRegister.clear();
        charTypedEventRegister.clear();

        renderEventRegister.clear();
        renderBackgroundEventRegister.clear();
        renderForegroundEventRegister.clear();
        renderTooltipsEventRegister.clear();
        renderDebugEventRegister.clear();

        widgetHiddenEventRegister.clear();
        widgetShownEventRegister.clear();
        widgetUpdatedEventRegister.clear();

        widgetAddedEventRegister.clear();
        widgetRemovedEventRegister.clear();

        widgetChildAddedEventRegister.clear();
        widgetChildRemovedEventRegister.clear();

        widgetMeasuredEventRegister.clear();
        widgetPositionedEventRegister.clear();
    }
}
