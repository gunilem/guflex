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
        headerWidget.getStyle().setWidth("100%").setHeight(Style.WRAP);
        headerWidget.defineName("Header");

        if (useDefaultCloseButton) {
            SpriteWidget closeButtonIcon = new SpriteWidget(Internals.getTextures().close_icon);
            closeButtonIcon.getStyle().setWidth("20").setHeight("20");

            ButtonWidget closeButton = new ButtonWidget();
            closeButton.onclick.register((a, b) -> close());
            closeButton.getStyle().setAnchor(Style.TOP_RIGHT).setPivot(Style.TOP_RIGHT);
            closeButton.addChild(closeButtonIcon);

            headerWidget.addChild(closeButton);
        }

        contentWidget = new FlexWidget();
        contentWidget.getStyle().setWidth("100%").setHeight(Style.AUTO).setFlexGrow(1);
        contentWidget.defineName("Content");

        addChild(headerWidget);
        addChild(contentWidget);

        getStyle().setWidth("175").setHeight("225");
        getStyle().setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px");
        getStyle().setFlexDirection(Style.VERTICAL).setPosition(Style.FIXED);

        setBackground(Internals.getTextures().button_background);

        eventHandler.registerMouseClickedUnconsumedEvent(this::onMouseClickedUnconsumed);
        eventHandler.registerMouseScrolledUnconsumedEvent(this::onMouseScrolledUnconsumed);
        eventHandler.registerMouseReleasedUnconsumedEvent(this::onMouseReleasedUnconsumed);
        eventHandler.registerMouseDraggedUnconsumedEvent(this::onMouseDraggedUnconsumed);
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
        getStyle().setX(xPosition);
        getStyle().setY(yPosition);
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

    protected boolean onMouseClickedUnconsumed(IMouseClickedEvent.Data event){
        if (rect().contains(event.mouseX(), event.mouseY())) return true;

        if (!outsideClickClose) return true;
        close();
        return true;
    }

    protected boolean onMouseReleasedUnconsumed(IMouseReleasedEvent.Data event){
        rect().contains(event.mouseX(), event.mouseY());
        return true;
    }

    protected boolean onMouseScrolledUnconsumed(IMouseScrolledEvent.Data event){
        rect().contains(event.mouseX(), event.mouseY());
        return true;
    }

    protected boolean onMouseDraggedUnconsumed(IMouseDraggedEvent.Data event){
        rect().contains(event.mouseX(), event.mouseY());
        return true;
    }
}
