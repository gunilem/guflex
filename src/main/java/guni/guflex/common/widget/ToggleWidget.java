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

        addStyle(Style.WIDTH, Style.WRAP);
        addStyle(Style.HEIGHT, Style.WRAP);
        addStyle(Style.PADDING_LEFT, "5px");
        addStyle(Style.PADDING_RIGHT, "5px");
        addStyle(Style.PADDING_TOP, "5px");
        addStyle(Style.PADDING_BOTTOM, "5px");
        addStyle(Style.ITEM_SPACING, "5px");

        toggleBox = new SpriteWidget(this.value ? Internals.getTextures().toggle_box_full : Internals.getTextures().toggle_box_empty);
        toggleBox.addStyle(Style.WIDTH, "15");
        toggleBox.addStyle(Style.HEIGHT, "15");
        addChild(toggleBox);

        eventHandler.registerMouseClickedEvent(this::onMouseClicked);
        eventHandler.registerWidgetRemovedEvent(this::onRemoved);
    }

    protected void onRemoved(){
        onValueChanged.release();
    }

    protected boolean onMouseClicked(IMouseClickedEvent.Data data){
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
