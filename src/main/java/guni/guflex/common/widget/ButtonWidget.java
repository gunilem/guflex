package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.IMouseClickedEvent;
import guni.guflex.api.event.register.two.EventRegister;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;

public class ButtonWidget extends FlexWidget {
    public final EventRegister<Double, Double> onclick;

    public ButtonWidget(){
        super();
        this.onclick = new EventRegister<>();

        getStyle().setWidth(Style.WRAP).setHeight(Style.WRAP);

        eventHandler.registerMouseClickedUnconsumedEvent(this::onMouseClickedUnconsumed);

        eventHandler.registerWidgetRemovedEvent(this::onRemoved);
    }

    protected void onRemoved(){
        onclick.release();
    }

    protected boolean onMouseClickedUnconsumed(IMouseClickedEvent.Data data){
        if (!rect().contains(data.mouseX(), data.mouseY())) return false;

        onclick.invoke(data.mouseX(), data.mouseY());
        return true;
    }
}
