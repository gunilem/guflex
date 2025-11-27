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

        addStyle(Style.WIDTH, Style.WRAP);
        addStyle(Style.HEIGHT, Style.WRAP);

        eventHandler.registerMouseClickedEvent(this::onMouseClicked);

        eventHandler.registerWidgetRemovedEvent(this::onRemoved);
    }

    protected void onRemoved(){
        onclick.release();
    }

    protected boolean onMouseClicked(IMouseClickedEvent.Data data){
        if (!rect().contains(data.mouseX(), data.mouseY())) return false;

        onclick.invoke(data.mouseX(), data.mouseY());
        return true;
    }
}
