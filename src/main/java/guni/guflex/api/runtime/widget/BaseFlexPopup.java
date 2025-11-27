package guni.guflex.api.runtime.widget;

import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.register.one.EventRegister;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.runtime.screen.IFlexScreen;
import guni.guflex.api.style.Style;
import guni.guflex.common.widget.ButtonWidget;
import guni.guflex.common.widget.SpriteWidget;
import guni.guflex.core.registers.Internals;
import net.minecraft.client.Minecraft;

public class BaseFlexPopup extends FlexWidget {
    public final EventRegister<IFlexWidget> onOpened;
    public final EventRegister<IFlexWidget> onClosed;

    public final FlexWidget headerWidget;
    public final FlexWidget contentWidget;

    private final int zOffset;
    protected boolean allowOtherInputs;
    protected boolean allowDefaultInputs;
    protected boolean outsideClickClose;

    public BaseFlexPopup(boolean allowOtherInputs, boolean allowDefaultInputs, boolean outsideClickClose, boolean useDefaultCloseButton){
        this(allowOtherInputs, allowDefaultInputs, outsideClickClose, useDefaultCloseButton, 250);
    }

    public BaseFlexPopup(boolean allowOtherInputs, boolean allowDefaultInputs, boolean outsideClickClose, boolean useDefaultCloseButton, int zOffset){
        super();

        this.allowOtherInputs = allowOtherInputs;
        this.allowDefaultInputs = allowDefaultInputs;
        this.outsideClickClose = outsideClickClose;

        this.zOffset = zOffset;

        this.onOpened = new EventRegister<>();
        this.onClosed = new EventRegister<>();

        headerWidget = new FlexWidget();
        headerWidget.addStyle(Style.WIDTH, "100%");
        headerWidget.addStyle(Style.HEIGHT, Style.WRAP);
        headerWidget.defineName("Header");

        if (useDefaultCloseButton) {
            SpriteWidget closeButtonIcon = new SpriteWidget(Internals.getTextures().close_icon);
            closeButtonIcon.addStyle(Style.WIDTH, "20");
            closeButtonIcon.addStyle(Style.HEIGHT, "20");

            ButtonWidget closeButton = new ButtonWidget();
            closeButton.onclick.register((a, b) -> close());
            closeButton.addStyle(Style.ANCHOR, Style.TOP_RIGHT);
            closeButton.addStyle(Style.PIVOT, Style.TOP_RIGHT);
            closeButton.addChild(closeButtonIcon);

            headerWidget.addChild(closeButton);
        }

        contentWidget = new FlexWidget();
        contentWidget.addStyle(Style.WIDTH, "100%");
        contentWidget.addStyle(Style.HEIGHT, Style.AUTO);
        contentWidget.addStyle(Style.FLEX_GROW, "1");
        contentWidget.defineName("Content");

        addChild(headerWidget);
        addChild(contentWidget);

        addStyle(Style.WIDTH, "175");
        addStyle(Style.HEIGHT, "225");

        addStyle(Style.PADDING_LEFT, "5px");
        addStyle(Style.PADDING_RIGHT, "5px");
        addStyle(Style.PADDING_TOP, "5px");
        addStyle(Style.PADDING_BOTTOM, "5px");

        addStyle(Style.FLEX_DIRECTION, Style.VERTICAL);
        addStyle(Style.POSITION, Style.FIXED);
        setBackground(Internals.getTextures().button_background);

        eventHandler.registerMouseClickedEvent(this::onMouseClicked);
        eventHandler.registerMouseScrolledEvent(this::onMouseScrolled);
        eventHandler.registerMouseReleasedEvent(this::onMouseReleased);
        eventHandler.registerMouseDraggedEvent(this::onMouseDragged);
    }

    @Override
    public void handleRenderEvent(IRenderEvent.Data event) {
        event.guiGraphics().pose().translate(0, 0, zOffset);
        super.handleRenderEvent(event);
        event.guiGraphics().pose().translate(0, 0, -zOffset);
    }

    @Override
    public void handleRenderBackgroundEvent(IRenderBackgroundEvent.Data event){
        event.guiGraphics().pose().translate(0, 0, zOffset);
        super.handleRenderBackgroundEvent(event);
        event.guiGraphics().pose().translate(0, 0, -zOffset);
    }

    @Override
    public void handleRenderForegroundEvent(IRenderForegroundEvent.Data event) {
        event.guiGraphics().pose().translate(0, 0, zOffset);
        super.handleRenderForegroundEvent(event);
        event.guiGraphics().pose().translate(0, 0, -zOffset);
    }

    @Override
    public boolean handleRenderTooltipsEvent(IRenderTooltipsEvent.Data event) {
        event.guiGraphics().pose().translate(0, 0, zOffset);
        if (super.handleRenderTooltipsEvent(event)){
            event.guiGraphics().pose().translate(0, 0, -zOffset);
            return true;
        }
        event.guiGraphics().pose().translate(0, 0, -zOffset);
        return false;
    }

    @Override
    public boolean handleRenderDebugEvent(IRenderDebugEvent.Data event) {
        event.guiGraphics().pose().translate(0, 0, zOffset);
        if (super.handleRenderDebugEvent(event)){
            event.guiGraphics().pose().translate(0, 0, -zOffset);
            return true;
        }
        event.guiGraphics().pose().translate(0, 0, -zOffset);
        return false;
    }


    public void open(int xPosition, int yPosition){
        addStyle(Style.X, xPosition + "");
        addStyle(Style.Y, yPosition + "");
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            screen.openPopup(this);
        }
        onOpened.invoke(this);
    }

    public void close(){
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen){
            screen.closePopup(this);
        }
        onClosed.invoke(this);
    }

    public boolean allowOtherInputs(){
        return allowOtherInputs;
    }

    public boolean allowDefaultInputs(){
        return allowDefaultInputs;
    }

    protected boolean onMouseClicked(IMouseClickedEvent.Data event){
        if (rect().contains(event.mouseX(), event.mouseY())) return true;

        if (!outsideClickClose) return true;
        close();
        return true;
    }

    protected boolean onMouseReleased(IMouseReleasedEvent.Data event){
        rect().contains(event.mouseX(), event.mouseY());
        return true;
    }

    protected boolean onMouseScrolled(IMouseScrolledEvent.Data event){
        rect().contains(event.mouseX(), event.mouseY());
        return true;
    }

    protected boolean onMouseDragged(IMouseDraggedEvent.Data event){
        rect().contains(event.mouseX(), event.mouseY());
        return true;
    }
}
