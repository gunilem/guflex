package guni.guflex.api.event;

import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.register.*;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.event.widgetEvents.*;
import guni.guflex.api.runtime.screen.IFlexScreen;
import net.minecraft.client.Minecraft;

public class FlexWidgetEventHandler {
    public final MouseEvent mouse = new MouseEvent();
    public final KeyEvent key = new KeyEvent();
    public final RenderEvent render = new RenderEvent();
    public final WidgetEvent widget = new WidgetEvent();

    public FlexScreenEventHandler screen() {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            return screen.screenEventHandler();
        }
        return null;
    }

    public static class MouseEvent{
        public final ConsumableEvent<IMouseClickedEvent.Data> clicked = new ConsumableEvent<>();
        public final ConsumableEvent<IMouseReleasedEvent.Data> released = new ConsumableEvent<>();
        public final ConsumableEvent<IMouseScrolledEvent.Data> scrolled = new ConsumableEvent<>();
        public final ConsumableEvent<IMouseDraggedEvent.Data> dragged = new ConsumableEvent<>();
        public final ConsumableEvent<IMouseMovedEvent.Data> moved = new ConsumableEvent<>();
    }
    public static class KeyEvent{
        public final ConsumableEvent<IKeyPressedEvent.Data> pressed = new ConsumableEvent<>();
        public final ConsumableEvent<IKeyReleasedEvent.Data> released = new ConsumableEvent<>();
        public final ConsumableEvent<ICharTypedEvent.Data> typed = new ConsumableEvent<>();
    }
    public static class RenderEvent{
        public final EventRegister1<IRenderEvent.Data> rendered = new EventRegister1<>();
        public final EventRegister1<IRenderBackgroundEvent.Data> background = new EventRegister1<>();
        public final EventRegister1<IRenderForegroundEvent.Data> foreground = new EventRegister1<>();
        public final EventConsumableRegister1<IRenderTooltipsEvent.Data> tooltips = new EventConsumableRegister1<>();
        public final EventConsumableRegister1<IRenderDebugEvent.Data> debug = new EventConsumableRegister1<>();
    }
    public static class WidgetEvent{
        public final EventRegister shown = new EventRegister();
        public final EventRegister hidden = new EventRegister();
        public final EventRegister added = new EventRegister();
        public final EventRegister removed = new EventRegister();
        public final EventRegister1<IWidgetChildAddedEvent.Data> childAdded = new EventRegister1<>();
        public final EventRegister1<IWidgetChildRemovedEvent.Data> childRemoved = new EventRegister1<>();
        public final EventRegister measured = new EventRegister();
        public final EventRegister positioned = new EventRegister();
        public final EventRegister updated = new EventRegister();
    }
    public static class ConsumableEvent<T>{
        public final EventRegister1<T> always = new EventRegister1<>();
        public final EventRegister1<T> consumed = new EventRegister1<>();
        public final EventConsumableRegister1<T> unconsumed = new EventConsumableRegister1<>();
    }
}
