package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.IMouseClickedEvent;
import guni.guflex.api.event.register.EventRegister2;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;

public class ButtonWidget extends FlexWidget {
    public final EventRegister2<Double, Double> onclick;

    public ButtonWidget(){
        super();
        this.onclick = new EventRegister2<>();

        getStyle().setWidth(Style.WRAP).setHeight(Style.WRAP);

        eventHandler.mouse.clicked.unconsumed.register(this::onMouseClickedUnconsumed);

        eventHandler.widget.removed.register(this::onRemoved);
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
