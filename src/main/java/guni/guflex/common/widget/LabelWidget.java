package guni.guflex.common.widget;

import guni.guflex.api.event.screenEvents.IRenderEvent;
import guni.guflex.api.event.screenEvents.IScreenTickEvent;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.Timer;
import java.util.TimerTask;

public class LabelWidget extends FlexWidget {
    protected Component text;
    protected Font font;
    protected boolean shadow;
    protected boolean multiline;
    protected boolean autoScroll;

    protected float xOffset = 0;

    public LabelWidget(Component text, boolean multiline, boolean autoScroll, boolean shadow) {
        super();
        this.text = text;
        this.shadow = shadow;
        this.multiline = multiline;
        this.autoScroll = autoScroll;

        this.font = Minecraft.getInstance().font;

        if (text == null) return;

        eventHandler.registerRenderEvent(this::onRender);

        if (!multiline) {
            getStyle().setWidth(font.width(text) + "px").setHeight(font.lineHeight + "px")
                            .setAnchor(Style.CENTER).setPivot(Style.CENTER);
        }
        else {
            eventHandler.registerWidgetMeasuredEvent(this::onMeasured);

            getStyle().setAnchor(Style.TOP_CENTER).setPivot(Style.TOP_CENTER);
        }
    }

    protected void onRender(IRenderEvent.Data event){
        if (!renderable()) return;
        if (multiline){
            event.guiGraphics().drawWordWrap(font, text, bounds.getX(), bounds.getY(), bounds.getWidth(), 0);
        }
        else if (font.width(text) - xOffset > bounds.getWidth()) {
            if (autoScroll) {
                drawScrollingText(event);
                return;
            }
            String textToDisplay = font.plainSubstrByWidth(text.getString(), bounds.getWidth() - font.width("..."));
            textToDisplay += "...";
            event.guiGraphics().drawString(font, Component.literal(textToDisplay).withStyle(text.getStyle()), bounds.getX(), bounds.getY(), 0, shadow);
        }
        else {
            event.guiGraphics().drawString(font, text, bounds.getX(), bounds.getY(), 0, shadow);
        }
    }

    public void changeText(Component text, boolean updateSize){
        this.text = text;
        if (updateSize && !multiline) {
            getStyle().setWidth(font.width(text) + "px").setHeight(font.lineHeight + "px");
        }
    }

    protected void onMeasured(){
        if (!multiline) return;
        getStyle().setHeight(font.wordWrapHeight(text, rect().getContentRect().getWidth()) + "px");
    }

    protected float scrollingSpeed = 0.1f;
    protected boolean waiting = false;

    protected void drawScrollingText(IRenderEvent.Data event){
        event.guiGraphics().enableScissor(
                rect().getX(),
                rect().getY(),
                rect().getX() + rect().getWidth(),
                rect().getY() + rect().getHeight()
        );
        event.guiGraphics().pose().translate(xOffset, 0, 0);

        Component textToDisplay;

        if (waiting) {
            textToDisplay = text;
        } else {
            xOffset -= scrollingSpeed;
            if (font.width(text) + xOffset > bounds.getWidth()) {
                String stringToDisplay = font.plainSubstrByWidth(text.getString(), (int) (bounds.getWidth() - xOffset - font.width("...")));
                stringToDisplay += "...";
                textToDisplay = Component.literal(stringToDisplay).withStyle(text.getStyle());
            }
            else {
                waiting = true;
                Timer timerAfter = new Timer();
                timerAfter.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        xOffset = 0;

                        Timer timerBefore = new Timer();
                        timerBefore.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                waiting = false;
                            }
                        }, 1500);
                    }
                }, 1500);
                textToDisplay = text;
            }
        }
        event.guiGraphics().drawString(font, textToDisplay, bounds.getX(), bounds.getY(), 0, shadow);


        event.guiGraphics().pose().translate(-xOffset, 0, 0);

        event.guiGraphics().disableScissor();
    }
}
