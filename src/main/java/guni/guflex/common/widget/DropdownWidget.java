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

        addStyle(Style.WIDTH, Style.WRAP);
        addStyle(Style.HEIGHT, Style.WRAP);

        valueLabel = new LabelWidget(Component.literal(currentValue.name()), false, false);
        addChild(valueLabel);

        addStyle(Style.PADDING_LEFT, "5px");
        addStyle(Style.PADDING_RIGHT, "5px");
        addStyle(Style.PADDING_TOP, "5px");
        addStyle(Style.PADDING_BOTTOM, "5px");

        eventHandler.registerMouseClickedEvent(this::onMouseClicked);

        eventHandler.registerWidgetRemovedEvent(this::onRemoved);
    }

    protected void onRemoved(){
        onValueChanged.release();
    }

    protected boolean onMouseClicked(IMouseClickedEvent.Data data){
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
        popup.addStyle(Style.WIDTH, "75");
        popup.addStyle(Style.HEIGHT,  Style.WRAP);
        popup.contentWidget.addStyle(Style.WIDTH, "100%");
        popup.contentWidget.addStyle(Style.HEIGHT, Style.WRAP);
        popup.contentWidget.addStyle(Style.FLEX_DIRECTION,  Style.VERTICAL);

        for (T value : data){
            LabelWidget label = new LabelWidget(Component.literal(value.name()), false, false);
            ButtonWidget button = new ButtonWidget();
            button.setBackground(Internals.getTextures().button_background);
            button.addStyle(Style.WIDTH, "100%");
            button.addStyle(Style.HEIGHT, Style.WRAP);
            button.addStyle(Style.BOX_SIZING_X, Style.BORDER_BOX);
            button.addStyle(Style.BOX_SIZING_Y, Style.CONTENT_BOX);
            button.addStyle(Style.PADDING_LEFT, "5px");
            button.addStyle(Style.PADDING_RIGHT, "5px");
            button.addStyle(Style.PADDING_TOP, "5px");
            button.addStyle(Style.PADDING_BOTTOM, "5px");
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