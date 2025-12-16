package guni.guflex.api.runtime.screen;

import guni.guflex.api.event.FlexScreenEventHandler;
import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.runtime.widget.BaseFlexPopup;
import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.api.runtime.widget.RootFlexWidget;
import guni.guflex.api.style.Style;
import guni.guflex.common.widget.*;
import guni.guflex.core.registers.Internals;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainerFlexScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IFlexScreen {
    protected FlexScreenEventHandler eventHandler = new FlexScreenEventHandler();
    protected RootFlexWidget root;
    protected RootFlexWidget popupRoot;
    protected IRenderDebugEvent.DebugInfo debugInfo;

    protected List<EmptyEvent> delayedOperation = new ArrayList<>();

    private int tickSinceLastInit;

    public AbstractContainerFlexScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        eventHandler.release();
    }

    @Override
    protected void init() {
        super.init();

        tickSinceLastInit = 0;

        root.setSize(width, height);
        popupRoot.setSize(width, height);

        computeWidgets();

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
        debugMenu.getStyle().setAnchor(Style.BOTTOM_LEFT);
        debugMenu.getStyle().setPivot(Style.BOTTOM_LEFT);

        debugMenu.getStyle().setMarginLeft("10px").setMarginBottom("10px")
                .setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px")
                .setWidth("15").setHeight("15");

        debugMenu.defineName("marginDebug");
        debugMenu.onclick.register(this::setupDebugPopup);
        debugMenu.addChild(debugMenuIcon);

        root.addChild(debugMenu);
    }

    @Override
    public void removed() {
        super.removed();
        root.handleRemovedEvent();
        popupRoot.handleRemovedEvent();
    }

    public void computeWidgets(){
        flushDelayedOperation();
        updateHierarchy();
    }

    private void setupDebugPopup(double mouseX, double mouseY){
        BaseFlexPopup popup = new BaseFlexPopup(false, false, true, false);
        popup.defineName("Debug Popup");

        popup.getStyle().setPivot(Style.BOTTOM_LEFT).setHeight(Style.WRAP);

        popup.contentWidget.getStyle().setHeight(Style.WRAP).setFlexDirection(Style.VERTICAL);

        LabelWidget popupTitle = new LabelWidget(Component.literal("Layout Debugger"), false, false, false);

        popupTitle.getStyle().setMarginLeft("5px").setMarginRight("5px").setMarginTop("5px").setMarginBottom("5px").setPivot(Style.TOP_CENTER);

        popup.contentWidget.addChild(popupTitle);

        DropdownWidget<IRenderDebugEvent.DebugInfo.DebugTrigger> debugTypeDropdownWidget = new DropdownWidget<>(IRenderDebugEvent.DebugInfo.DebugTrigger.values());
        debugTypeDropdownWidget.setBackground(Internals.getTextures().button_background);
        debugTypeDropdownWidget.defineName("Debug Type Dropdown");
        debugTypeDropdownWidget.getStyle().setWidth("100%").setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.CONTENT_BOX);
        debugTypeDropdownWidget.onValueChanged.register(debugInfo::setDebugTrigger);
        popup.contentWidget.addChild(debugTypeDropdownWidget);

        LabelWidget marginToggleLabel = new LabelWidget(Component.literal("Margin"), false, false, false);
        ToggleWidget marginToggle = new ToggleWidget(debugInfo.marginDebug);
        marginToggle.setBackground(Internals.getTextures().button_background);
        marginToggle.defineName("Margin Toggle");
        marginToggle.getStyle().setWidth("100%").setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.CONTENT_BOX);
        marginToggle.addChild(marginToggleLabel);
        marginToggle.onValueChanged.register(value -> debugInfo.marginDebug = value);
        popup.contentWidget.addChild(marginToggle);

        LabelWidget borderToggleLabel = new LabelWidget(Component.literal("Border"), false, false, false);
        ToggleWidget borderToggle = new ToggleWidget(debugInfo.borderDebug);
        borderToggle.setBackground(Internals.getTextures().button_background);
        borderToggle.defineName("Margin Toggle");
        borderToggle.getStyle().setWidth("100%").setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.CONTENT_BOX);
        borderToggle.addChild(borderToggleLabel);
        borderToggle.onValueChanged.register(value -> debugInfo.borderDebug = value);
        popup.contentWidget.addChild(borderToggle);

        LabelWidget paddingToggleLabel = new LabelWidget(Component.literal("Padding"), false, false, false);
        ToggleWidget paddingToggle = new ToggleWidget(debugInfo.paddingDebug);
        paddingToggle.setBackground(Internals.getTextures().button_background);
        paddingToggle.defineName("Margin Toggle");
        paddingToggle.getStyle().setWidth("100%").setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.CONTENT_BOX);
        paddingToggle.addChild(paddingToggleLabel);
        paddingToggle.onValueChanged.register(value -> debugInfo.paddingDebug = value);
        popup.contentWidget.addChild(paddingToggle);

        LabelWidget debugBoxToggleLabel = new LabelWidget(Component.literal("Debug Box"), false, false, false);
        ToggleWidget debugBoxToggle = new ToggleWidget(debugInfo.displayDebugBox);
        debugBoxToggle.setBackground(Internals.getTextures().button_background);
        debugBoxToggle.defineName("Margin Toggle");
        debugBoxToggle.getStyle().setWidth("100%").setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.CONTENT_BOX);
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
    }

    @Override
    public void closePopup(BaseFlexPopup popup) {
        popupRoot.removeChild(popup);
    }

    protected boolean needUpdate;

    @Override
    public void onHierarchyUpdated(){
        needUpdate = true;
    }
    @Override
    public void addDelayOperation(EmptyEvent event){
        delayedOperation.add(event);
    }

    protected void flushDelayedOperation(){
        for (EmptyEvent event : delayedOperation) {
            event.invoke();
        }
        delayedOperation.clear();
    }

    protected void updateHierarchy(){
        if (root.checkDirty()) root.recomputeLayout(null);
        if (popupRoot.checkDirty()) popupRoot.recomputeLayout(null);

        needUpdate = false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        IMouseClickedEvent.Data eventData = new IMouseClickedEvent.Data(button, mouseX, mouseY);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleMouseClickedEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()){
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                return true;
            }
        }
        consumed = root.handleMouseClickedEvent(eventData, consumed);
        if (!consumed) return super.mouseClicked(mouseX, mouseY, button);
        return true;
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        IMouseReleasedEvent.Data eventData = new IMouseReleasedEvent.Data(button, mouseX, mouseY);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleMouseReleasedEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()){
                    return super.mouseReleased(mouseX, mouseY, button);
                }
                return true;
            }
        }
        consumed = root.handleMouseReleasedEvent(eventData, consumed);
        if (!consumed) return super.mouseReleased(mouseX, mouseY, button);
        return true;
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        IMouseDraggedEvent.Data eventData = new IMouseDraggedEvent.Data(button, mouseX, mouseY, dragX, dragY);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleMouseDraggedEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()){
                    return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
                }
                return true;
            }
        }
        consumed = root.handleMouseDraggedEvent(eventData, consumed);
        if (!consumed) return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        return true;
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        IMouseScrolledEvent.Data eventData = new IMouseScrolledEvent.Data(mouseX, mouseY, scrollX, scrollY);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleMouseScrolledEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()){
                    return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
                }
                return true;
            }
        }
        consumed = root.handleMouseScrolledEvent(eventData, consumed);
        if (!consumed) return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        return true;
    }
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        IMouseMovedEvent.Data eventData = new IMouseMovedEvent.Data(mouseX, mouseY);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleMouseMovedEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()) super.mouseMoved(mouseX, mouseY);
                return;
            }
        }
        consumed = root.handleMouseMovedEvent(eventData, consumed);
        if (!consumed) super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        IKeyPressedEvent.Data eventData = new IKeyPressedEvent.Data(keyCode, scanCode, modifiers);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleKeyPressedEvent(eventData, consumed);
            if (consumed){
                if (!popup.allowOtherInputs()){
                    if (popup.allowDefaultInputs()){
                        return super.keyPressed(keyCode, scanCode, modifiers);
                    }
                    return true;
                }
                else {
                    consumed = false;
                }
            }
        }
        consumed = root.handleKeyPressedEvent(eventData, consumed);
        if (!consumed) return super.keyPressed(keyCode, scanCode, modifiers);
        return true;
    }
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        IKeyReleasedEvent.Data eventData = new IKeyReleasedEvent.Data(keyCode, scanCode, modifiers);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleKeyReleasedEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()){
                    return super.keyReleased(keyCode, scanCode, modifiers);
                }
                return true;
            }
        }
        consumed = root.handleKeyReleasedEvent(eventData, consumed);
        if (!consumed) return super.keyReleased(keyCode, scanCode, modifiers);
        return true;
    }
    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        ICharTypedEvent.Data eventData = new ICharTypedEvent.Data(codePoint, modifiers);

        boolean consumed = false;
        for (int i = popupRoot.children().size() -1; i >= 0; i--){
            IFlexWidget widget = popupRoot.children().get(i);
            if (!widget.renderable()) continue;
            if (!(widget instanceof BaseFlexPopup popup)) continue;
            consumed = popup.handleCharTypedEvent(eventData, consumed);
            if (!popup.allowOtherInputs()){
                if (popup.allowDefaultInputs()){
                    return super.charTyped(codePoint, modifiers);
                }
                return true;
            }
        }
        consumed = root.handleCharTypedEvent(eventData, consumed);
        if (!consumed) return super.charTyped(codePoint, modifiers);
        return true;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        tickSinceLastInit++;
        eventHandler.invoke(new IScreenTickEvent.Data(tickSinceLastInit));
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.pose().translate(-leftPos, -topPos, 0);
        IRenderBackgroundEvent.Data backgroundData = new IRenderBackgroundEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderEvent.Data renderData = new IRenderEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderForegroundEvent.Data foregroundData = new IRenderForegroundEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        IRenderDebugEvent.Data debugData = new IRenderDebugEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0, debugInfo);

        popupRoot.handleRenderBackgroundEvent(backgroundData);
        popupRoot.handleRenderEvent(renderData);
        popupRoot.handleRenderForegroundEvent(foregroundData);

        if (debugData.info().debugTrigger != IRenderDebugEvent.DebugInfo.DebugTrigger.Never && !popupRoot.handleRenderDebugEvent(debugData)){
            root.handleRenderDebugEvent(debugData);
        }
        guiGraphics.pose().translate(leftPos, topPos, 0);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        IRenderTooltipsEvent.Data tooltipsData = new IRenderTooltipsEvent.Data(guiGraphics, 0, mouseX, mouseY, 0, 0);
        if (!popupRoot.handleRenderTooltipsEvent(tooltipsData)){
            if (!root.handleRenderTooltipsEvent(tooltipsData)){
                super.renderTooltip(guiGraphics, mouseX, mouseY);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        flushDelayedOperation();
        if (needUpdate){
            updateHierarchy();
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        IRenderBackgroundEvent.Data backgroundData = new IRenderBackgroundEvent.Data(guiGraphics, partialTick, mouseX, mouseY, 0, 0);
        IRenderEvent.Data renderData = new IRenderEvent.Data(guiGraphics, partialTick, mouseX, mouseY, 0, 0);
        IRenderForegroundEvent.Data foregroundData = new IRenderForegroundEvent.Data(guiGraphics, partialTick, mouseX, mouseY, 0, 0);

        root.handleRenderBackgroundEvent(backgroundData);
        root.handleRenderEvent(renderData);
        root.handleRenderForegroundEvent(foregroundData);
    }
}
