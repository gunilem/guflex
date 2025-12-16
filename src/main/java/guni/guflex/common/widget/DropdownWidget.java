package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.IMouseClickedEvent;
import guni.guflex.api.event.register.one.EventRegister;
import guni.guflex.api.runtime.widget.BaseFlexPopup;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;
import guni.guflex.core.registers.Internals;
import net.minecraft.network.chat.Component;

public class DropdownWidget<T extends Enum<?>> extends FlexWidget {
    public final EventRegister<T> onValueChanged;

    protected LabelWidget valueLabel;
    protected T[] data;

    protected T currentValue;

    public DropdownWidget(T[] data){
        super();
        this.data = data;
        this.onValueChanged = new EventRegister<>();

        currentValue = data[0];


        valueLabel = new LabelWidget(Component.literal(currentValue.name()), false, false, false);
        addChild(valueLabel);

        getStyle().setWidth(Style.WRAP).setHeight(Style.WRAP)
                .setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px");

        eventHandler.registerMouseClickedUnconsumedEvent(this::onMouseClickedUnconsumed);

        eventHandler.registerWidgetRemovedEvent(this::onRemoved);
    }

    protected void onRemoved(){
        onValueChanged.release();
    }

    protected boolean onMouseClickedUnconsumed(IMouseClickedEvent.Data data){
        if (!rect().contains(data.mouseX(), data.mouseY())) return false;

        openPopup();
        return true;
    }

    public void switchValue(T value){
        currentValue = value;
        valueLabel.changeText(Component.literal(currentValue.name()), true);
        onValueChanged.invoke(currentValue);
    }

    protected void openPopup(){
        BaseFlexPopup popup = new BaseFlexPopup(false, false, true, false, 1500);
        popup.defineName("Dropdown popup");
        popup.getStyle().setWidth("75").setHeight(Style.WRAP);
        popup.contentWidget.getStyle().setWidth("100%").setHeight(Style.WRAP)
                .setFlexDirection(Style.VERTICAL);

        for (T value : data){
            LabelWidget label = new LabelWidget(Component.literal(value.name()), false, false, false);
            ButtonWidget button = new ButtonWidget();
            button.setBackground(Internals.getTextures().button_background);
            button.getStyle().setWidth("100%").setHeight(Style.WRAP)
                    .setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.CONTENT_BOX)
                    .setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px");
            button.addChild(label);
            button.onclick.register((a, b) -> {
                switchValue(value);
                popup.close();
            });
            popup.contentWidget.addChild(button);
        }

        popup.open(rect().getX() + rect().getWidth() - 85, rect().getY() + rect().getHeight());
    }
}