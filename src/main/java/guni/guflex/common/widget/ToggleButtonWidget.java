package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.IMouseClickedEvent;
import guni.guflex.api.event.register.one.EventRegister;
import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.core.registers.Internals;

public class ToggleButtonWidget extends ButtonWidget {
    public final EventRegister<Boolean> onValueChanged;

    protected IFlexWidget onDisplay;
    protected IFlexWidget offDisplay;

    protected boolean value;

    public ToggleButtonWidget(boolean baseValue, IFlexWidget onDisplay, IFlexWidget offDisplay){
        super();
        this.value = baseValue;
        onDisplay.hide();
        this.onDisplay = onDisplay;
        offDisplay.hide();
        this.offDisplay = offDisplay;
        addChild(onDisplay);
        addChild(offDisplay);

        updateBackground();
        onValueChanged = new EventRegister<>();
    }

    @Override
    protected boolean onMouseClicked(IMouseClickedEvent.Data data){
        if (super.onMouseClicked(data)){
            value = !value;
            onValueChanged.invoke(value);
            updateBackground();
            return true;
        }
        return false;
    }

    protected void updateBackground(){
        if (value){
            setBackground(Internals.getTextures().button_background);
            onDisplay.show();
            offDisplay.hide();
        }
        else {
            setBackground(Internals.getTextures().button_background_off);
            offDisplay.show();
            onDisplay.hide();
        }
    }
}
