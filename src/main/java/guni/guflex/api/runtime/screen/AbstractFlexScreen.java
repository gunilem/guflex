package guni.guflex.api.runtime.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AbstractFlexScreen extends Screen {
    protected AbstractFlexScreen(Component title) {
        super(title);
    }
    /* implements IFlexScreen {
    protected FlexScreenEventHandler eventHandler = new FlexScreenEventHandler();
    protected RootFlexWidget root;
    protected RootFlexWidget popupRoot;
    protected IRenderDebugEvent.DebugInfo debugInfo;

    private int tickSinceLastInit;
    private List<IFlexWidget> releasedPopups = new ArrayList<>();

    public AbstractFlexScreen(Component title) {
        super(title);

        eventHandler.release();
    }

    @Override
    protected void init() {
        super.init();

        tickSinceLastInit = 0;

        root.setSize(width, height);
        root.setDirty();
        popupRoot.setSize(width, height);
        popupRoot.setDirty();

        root.checkForDirty(null);
        popupRoot.checkForDirty(null);
        onLayoutUpdated();

        eventHandler.invoke(new IScreenInitEvent.Data(this));
    }

    @Override
    public void added() {
        super.added();

        debugInfo = new IRenderDebugEvent.DebugInfo();

        root = new RootFlexWidget();
        root.defineName("root");

        popupRoot = new RootFlexWidget();
        popupRoot.defineName("popup root");

        SpriteWidget debugMenuIcon = new SpriteWidget(Internals.getTextures().menu_icon);
        debugMenuIcon.defineName("debugMenuIcon");

        ButtonWidget debugMenu = new ButtonWidget();
        debugMenu.setBackground(Internals.getTextures().button_background);
        debugMenu.addStyle(Style.ANCHOR, Style.BOTTOM_LEFT);
        debugMenu.addStyle(Style.PIVOT, Style.BOTTOM_LEFT);

        debugMenu.addStyle(Style.MARGIN_LEFT, "10px");
        debugMenu.addStyle(Style.MARGIN_BOTTOM, "10px");

        debugMenu.addStyle(Style.PADDING_LEFT, "5px");
        debugMenu.addStyle(Style.PADDING_RIGHT, "5px");
        debugMenu.addStyle(Style.PADDING_BOTTOM, "5px");
        debugMenu.addStyle(Style.PADDING_TOP, "5px");

        debugMenu.addStyle(Style.WIDTH, "15");
        debugMenu.addStyle(Style.HEIGHT, "15");

        debugMenu.defineName("marginDebug");
        debugMenu.onclick.register(this::setupDebugPopup);
        debugMenu.addChild(debugMenuIcon);

        root.addChild(debugMenu);
    }

    @Override
    public void removed() {
        super.removed();
        root.removeAllChild();
        popupRoot.removeAllChild();
    }

    private void setupDebugPopup(double mouseX, double mouseY){
        BaseFlexPopup popup = new BaseFlexPopup(false, false, true, false);
        popup.defineName("Debug Popup");
        popup.addStyle(Style.FLEX_DIRECTION, Style.VERTICAL);
        popup.addStyle(Style.PIVOT, Style.BOTTOM_LEFT);
        popup.addStyle(Style.HEIGHT,  Style.WRAP);
        popup.contentWidget.addStyle(Style.HEIGHT,  Style.WRAP);
        popup.contentWidget.addStyle(Style.FLEX_DIRECTION,  Style.VERTICAL);

        LabelWidget popupTitle = new LabelWidget(Component.literal("Layout Debugger"), false, false);
        popupTitle.addStyle(Style.MARGIN_LEFT, "5px");
        popupTitle.addStyle(Style.MARGIN_RIGHT, "5px");
        popupTitle.addStyle(Style.MARGIN_BOTTOM, "5px");
        popupTitle.addStyle(Style.MARGIN_TOP, "5px");
        popupTitle.addStyle(Style.PIVOT, Style.TOP_CENTER);
        popup.contentWidget.addChild(popupTitle);

        LabelWidget marginToggleLabel = new LabelWidget(Component.literal("Margin"), false, false);
        ToggleWidget marginToggle = new ToggleWidget(debugInfo.marginDebug);
        marginToggle.setBackground(Internals.getTextures().button_background);
        marginToggle.defineName("Margin Toggle");
        marginToggle.addStyle(Style.WIDTH, "100%");
        marginToggle.addStyle(Style.BOX_SIZING_X, Style.BORDER_BOX);
        marginToggle.addStyle(Style.BOX_SIZING_Y, Style.CONTENT_BOX);
        marginToggle.addChild(marginToggleLabel);
        marginToggle.onValueChanged.register(value -> debugInfo.marginDebug = value);
        popup.contentWidget.addChild(marginToggle);

        LabelWidget borderToggleLabel = new LabelWidget(Component.literal("Border"), false, false);
        ToggleWidget borderToggle = new ToggleWidget(debugInfo.borderDebug);
        borderToggle.setBackground(Internals.getTextures().button_background);
        borderToggle.defineName("Margin Toggle");
        borderToggle.addStyle(Style.WIDTH, "100%");
        borderToggle.addStyle(Style.BOX_SIZING_X, Style.BORDER_BOX);
        borderToggle.addStyle(Style.BOX_SIZING_Y, Style.CONTENT_BOX);
        borderToggle.addChild(borderToggleLabel);
        borderToggle.onValueChanged.register(value -> debugInfo.borderDebug = value);
        popup.contentWidget.addChild(borderToggle);

        LabelWidget paddingToggleLabel = new LabelWidget(Component.literal("Padding"), false, false);
        ToggleWidget paddingToggle = new ToggleWidget(debugInfo.paddingDebug);
        paddingToggle.setBackground(Internals.getTextures().button_background);
        paddingToggle.defineName("Margin Toggle");
        paddingToggle.addStyle(Style.WIDTH, "100%");
        paddingToggle.addStyle(Style.BOX_SIZING_X, Style.BORDER_BOX);
        paddingToggle.addStyle(Style.BOX_SIZING_Y, Style.CONTENT_BOX);
        paddingToggle.addChild(paddingToggleLabel);
        paddingToggle.onValueChanged.register(value -> debugInfo.paddingDebug = value);
        popup.contentWidget.addChild(paddingToggle);

        LabelWidget debugBoxToggleLabel = new LabelWidget(Component.literal("Debug Box"), false, false);
        ToggleWidget debugBoxToggle = new ToggleWidget(debugInfo.displayDebugBox);
        debugBoxToggle.setBackground(Internals.getTextures().button_background);
        debugBoxToggle.defineName("Margin Toggle");
        debugBoxToggle.addStyle(Style.WIDTH, "100%");
        debugBoxToggle.addStyle(Style.BOX_SIZING_X, Style.BORDER_BOX);
        debugBoxToggle.addStyle(Style.BOX_SIZING_Y, Style.CONTENT_BOX);
        debugBoxToggle.addChild(debugBoxToggleLabel);
        debugBoxToggle.onValueChanged.register(value -> debugInfo.displayDebugBox = value);
        popup.contentWidget.addChild(debugBoxToggle);


        popup.open((int)mouseX, (int)mouseY);
    }

    @Override
    public FlexScreenEventHandler screenEventHandler() {
        return eventHandler;
    }
    @Override
    public IFlexWidget root() {
        return root;
    }

    @Override
    public void openPopup(BaseFlexPopup popup) {
        popupRoot.addChild(popup);
        popup.setDirty();
    }

    @Override
    public void closePopup(BaseFlexPopup popup) {
        releasedPopups.add(popup);
    }

    protected void flushReleasedPopups(){
        for (IFlexWidget popup : releasedPopups){
            popupRoot.removeChild(popup);
        }
        releasedPopups.clear();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        IMouseClickedEvent.Data eventData = new IMouseClickedEvent.Data(button, mouseX, mouseY);
        IMouseClickedConsumedEvent.Data eventConsumedData = new IMouseClickedConsumedEvent.Data(button, mouseX, mouseY);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.mouseClicked(mouseX, mouseY, button);
            }
        }
        return true;
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        IMouseReleasedEvent.Data eventData = new IMouseReleasedEvent.Data(button, mouseX, mouseY);
        IMouseReleasedConsumedEvent.Data eventConsumedData = new IMouseReleasedConsumedEvent.Data(button, mouseX, mouseY);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.mouseReleased(mouseX, mouseY, button);
            }
        }
        return true;
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        IMouseDraggedEvent.Data eventData = new IMouseDraggedEvent.Data(button, mouseX, mouseY, dragX, dragY);
        IMouseDraggedConsumedEvent.Data eventConsumedData = new IMouseDraggedConsumedEvent.Data(button, mouseX, mouseY, dragX, dragY);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
            }
        }
        return true;
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        IMouseScrolledEvent.Data eventData = new IMouseScrolledEvent.Data(mouseX, mouseY, scrollX, scrollY);
        IMouseScrolledConsumedEvent.Data eventConsumedData = new IMouseScrolledConsumedEvent.Data(mouseX, mouseY, scrollX, scrollY);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
            }
        }
        return true;
    }
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        IMouseMovedEvent.Data eventData = new IMouseMovedEvent.Data(mouseX, mouseY);
        eventHandler.invoke(eventData);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        IKeyPressedEvent.Data eventData = new IKeyPressedEvent.Data(keyCode, scanCode, modifiers);
        IKeyPressedConsumedEvent.Data eventConsumedData = new IKeyPressedConsumedEvent.Data(keyCode, scanCode, modifiers);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        return true;
    }
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        IKeyReleasedEvent.Data eventData = new IKeyReleasedEvent.Data(keyCode, scanCode, modifiers);
        IKeyReleasedConsumedEvent.Data eventConsumedData = new IKeyReleasedConsumedEvent.Data(keyCode, scanCode, modifiers);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.keyReleased(keyCode, scanCode, modifiers);
            }
        }
        return true;
    }
    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        ICharTypedEvent.Data eventData = new ICharTypedEvent.Data(codePoint, modifiers);
        ICharTypedConsumedEvent.Data eventConsumedData = new ICharTypedConsumedEvent.Data(codePoint, modifiers);
        eventHandler.invoke(eventConsumedData);
        if (!popupRoot.eventHandler().invoke(eventData, popupRoot)){
            if (!root.eventHandler().invoke(eventData, root)){
                return super.charTyped(codePoint, modifiers);
            }
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        tickSinceLastInit++;
        eventHandler.invoke(new IScreenTickEvent.Data(tickSinceLastInit));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        IRenderBackgroundEvent.Data backgroundData = new IRenderBackgroundEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderEvent.Data renderData = new IRenderEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderForegroundEvent.Data foregroundData = new IRenderForegroundEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderTooltipsEvent.Data tooltipsData = new IRenderTooltipsEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderDebugEvent.Data debugData = new IRenderDebugEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0, debugInfo);

        if (popupRoot.checkForDirty(null)){
            onLayoutUpdated();
        }
        popupRoot.eventHandler().invoke(backgroundData, popupRoot);
        popupRoot.eventHandler().invoke(renderData, popupRoot);
        popupRoot.eventHandler().invoke(foregroundData, popupRoot);

        if (!popupRoot.eventHandler().invoke(tooltipsData, popupRoot)){
            root.eventHandler().invoke(tooltipsData, root);
        }
        if (!popupRoot.eventHandler().invoke(debugData, popupRoot)){
            root.eventHandler().invoke(debugData, root);
        }
        flushReleasedPopups();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        if (root.checkForDirty(null)){
            onLayoutUpdated();
        }

        IRenderBackgroundEvent.Data backgroundData = new IRenderBackgroundEvent.Data(guiGraphics, partialTick, mouseX, mouseY, 0, 0);
        IRenderEvent.Data renderData = new IRenderEvent.Data(guiGraphics, partialTick, mouseX, mouseY, 0, 0);
        IRenderForegroundEvent.Data foregroundData = new IRenderForegroundEvent.Data(guiGraphics, partialTick, mouseX, mouseY, 0, 0);

        root.eventHandler().invoke(backgroundData, root);
        root.eventHandler().invoke(renderData, root);
        root.eventHandler().invoke(foregroundData, root);
    }

    public void onLayoutUpdated(){

    }*/
}
