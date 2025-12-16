package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.IMouseClickedEvent;
import guni.guflex.api.event.register.one.EventRegister;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;
import guni.guflex.core.registers.Internals;

public class ToggleWidget extends FlexWidget {
    public final EventRegister<Boolean> onValueChanged;

    private boolean value;
    private final SpriteWidget toggleBox;

    public ToggleWidget(boolean value){
        super();
        this.onValueChanged = new EventRegister<>();

        this.value = value;

        getStyle()
                .setWidth(Style.WRAP).setHeight(Style.WRAP)
                .setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px")
                .setItemSpacing("5px");

        toggleBox = new SpriteWidget(this.value ? Internals.getTextures().toggle_box_full : Internals.getTextures().toggle_box_empty);
        toggleBox.getStyle()
                .setWidth("15").setHeight("15");
        addChild(toggleBox);

        eventHandler.registerMouseClickedUnconsumedEvent(this::onMouseClickedUnconsumed);
        eventHandler.registerWidgetRemovedEvent(this::onRemoved);
    }

    protected void onRemoved(){
        onValueChanged.release();
    }

    protected boolean onMouseClickedUnconsumed(IMouseClickedEvent.Data data){
        if (!rect().contains(data.mouseX(), data.mouseY())) return false;

        switchValue();

        return true;
    }

    private void switchValue(){
        value = !value;
        onValueChanged.invoke(value);

        toggleBox.changeSprite(this.value ? Internals.getTextures().toggle_box_full : Internals.getTextures().toggle_box_empty);
    }
}
