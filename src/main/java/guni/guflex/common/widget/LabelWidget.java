package guni.guflex.common.widget;

import guni.guflex.api.event.screenEvents.IRenderEvent;
import guni.guflex.api.event.widgetEvents.IWidgetMeasuredEvent;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class LabelWidget extends FlexWidget {
    protected Component text;
    protected Font font;
    protected boolean shadow;
    protected boolean multiline;

    public LabelWidget(Component text, boolean multiline, boolean shadow) {
        super();
        this.text = text;
        this.shadow = shadow;
        this.multiline = multiline;

        this.font = Minecraft.getInstance().font;

        if (text == null) return;

        eventHandler.registerRenderEvent(this::onRender);

        if (!multiline) {
            addStyle(Style.WIDTH, font.width(text) + "px");
            addStyle(Style.HEIGHT, font.lineHeight + "px");

            addStyle(Style.ANCHOR, Style.CENTER);
            addStyle(Style.PIVOT, Style.CENTER);
        }
        else {
            eventHandler.registerWidgetMeasuredEvent(this::onMeasured);

            addStyle(Style.ANCHOR, Style.TOP_CENTER);
            addStyle(Style.PIVOT, Style.TOP_CENTER);
        }
    }

    protected void onRender(IRenderEvent.Data event){
        if (!renderable()) return;
        if (multiline){
            event.guiGraphics().drawWordWrap(font, text, bounds.getX(), bounds.getY(), bounds.getWidth(), 0);
            return;
        }
        if (font.width(text) > bounds.getWidth()){
            String textToDisplay = font.plainSubstrByWidth(text.getString(), bounds.getWidth() - font.width("..."));
            textToDisplay += "...";
            event.guiGraphics().drawString(font, Component.literal(textToDisplay).withStyle(text.getStyle()), bounds.getX(), bounds.getY(), 1, shadow);
        }
        else {
            event.guiGraphics().drawString(font, text, bounds.getX(), bounds.getY(), 0, shadow);
        }
    }

    public void changeText(Component text, boolean updateSize){
        this.text = text;
        if (updateSize && !multiline) {
            addStyle(Style.WIDTH, font.width(text) + "px");
            addStyle(Style.HEIGHT, font.lineHeight + "px");
            setDirty();
        }
    }

    protected void onMeasured(){
        if (!multiline) return;
        addStyle(Style.HEIGHT, font.wordWrapHeight(text, rect().getContentRect().getWidth()) + "px");
    }
}
