package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.Style;
import guni.guflex.common.event.widget.scrollingView.IScrollingViewSearchable;

import java.util.ArrayList;
import java.util.List;

public class ScrollingViewWidget extends FlexWidget {
    public enum ScrollingDirection{
        Horizontal,
        Vertical,
        Both
    }

    private String currentSearchData = "";

    public List<FlexWidget> allItems;
    public final FlexWidget contentWidget;
    protected ScrollingDirection scrollingDirection;
    protected int yOffset = 0;
    protected int xOffset = 0;
    protected int scrollYSensibility = 10;
    protected int scrollXSensibility = 10;

    public ScrollingViewWidget(ScrollingDirection scrollingDirection){
        super();
        this.scrollingDirection = scrollingDirection;
        this.allItems = new ArrayList<>();

        contentWidget = new FlexWidget();
        contentWidget.getStyle()
                .setWidth(scrollingDirection != ScrollingDirection.Horizontal ? "100%" : Style.WRAP)
                .setHeight(scrollingDirection != ScrollingDirection.Vertical ? "100%" : Style.WRAP)
                .setFlexDirection(Style.VERTICAL);
        addChild(contentWidget);

        getStyle()
                .setWidth("100%").setHeight("100%")
                .setFlexDirection(Style.VERTICAL)
                .setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px");

        eventHandler.registerMouseClickedUnconsumedEvent(this::onMouseClickedUnconsumed);
        eventHandler.registerMouseReleasedUnconsumedEvent(this::onMouseReleasedUnconsumed);
        eventHandler.registerMouseScrolledUnconsumedEvent(this::onMouseScrolledUnconsumed);
        eventHandler.registerMouseDraggedUnconsumedEvent(this::onMouseDraggedUnconsumed);
        eventHandler.registerMouseClickedConsumedEvent(this::onMouseClickedConsumed);
    }

    @Override
    public void handleRenderEvent(IRenderEvent.Data event) {
        if (!renderable()) return;
        eventHandler.invokeRenderEvent(event);

        FlexRect contentRect = rect().getContentRect();
        event.guiGraphics().enableScissor(
                contentRect.getX() + event.parentXOffset(),
                contentRect.getY() + event.parentYOffset(),
                contentRect.getX() + contentRect.getWidth() + event.parentXOffset() + 1,
                contentRect.getY() + contentRect.getHeight()  + event.parentYOffset() + 1
        );
        IRenderEvent.Data newData = new IRenderEvent.Data(
                event.guiGraphics(),
                event.partialTick(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.parentXOffset() - this.xOffset,
                event.parentYOffset() - this.yOffset
        );

        event.guiGraphics().pose().translate(-this.xOffset, -this.yOffset, 0);

        for (IFlexWidget child : children){
            child.handleRenderEvent(newData);
        }

        event.guiGraphics().pose().translate(this.xOffset, this.yOffset, 0);

        event.guiGraphics().disableScissor();
    }
    @Override
    public void handleRenderBackgroundEvent(IRenderBackgroundEvent.Data event) {
        if (!renderable()) return;
        eventHandler.invokeRenderBackgroundEvent(event);

        FlexRect contentRect = rect().getContentRect();

        event.guiGraphics().enableScissor(
                contentRect.getX() + event.parentXOffset(),
                contentRect.getY() + event.parentYOffset(),
                contentRect.getX() + contentRect.getWidth() + event.parentXOffset() + 1,
                contentRect.getY() + contentRect.getHeight()  + event.parentYOffset() + 1
        );
        IRenderBackgroundEvent.Data newData = new IRenderBackgroundEvent.Data(
                event.guiGraphics(),
                event.partialTick(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.parentXOffset() - this.xOffset,
                event.parentYOffset() - this.yOffset
        );

        event.guiGraphics().pose().translate(-this.xOffset, -this.yOffset, 0);
        for (IFlexWidget child : children){
            child.handleRenderBackgroundEvent(newData);
        }

        event.guiGraphics().pose().translate(this.xOffset, this.yOffset, 0);

        event.guiGraphics().disableScissor();
    }
    @Override
    public void handleRenderForegroundEvent(IRenderForegroundEvent.Data event) {
        if (!renderable()) return;
        eventHandler.invokeRenderForegroundEvent(event);

        FlexRect contentRect = rect().getContentRect();
        event.guiGraphics().enableScissor(
                contentRect.getX() + event.parentXOffset(),
                contentRect.getY() + event.parentYOffset(),
                contentRect.getX() + contentRect.getWidth() + event.parentXOffset() + 1,
                contentRect.getY() + contentRect.getHeight()  + event.parentYOffset() + 1
        );
        IRenderForegroundEvent.Data newData = new IRenderForegroundEvent.Data(
                event.guiGraphics(),
                event.partialTick(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.parentXOffset() - this.xOffset,
                event.parentYOffset() - this.yOffset
        );

        event.guiGraphics().pose().translate(-this.xOffset, -this.yOffset, 0);

        for (IFlexWidget child : children){
            child.handleRenderForegroundEvent(newData);
        }

        event.guiGraphics().pose().translate(this.xOffset, this.yOffset, 0);

        event.guiGraphics().disableScissor();
    }
    @Override
    public boolean handleRenderTooltipsEvent(IRenderTooltipsEvent.Data event){
        IRenderTooltipsEvent.Data newData = new IRenderTooltipsEvent.Data(
                event.guiGraphics(),
                event.partialTick(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.parentXOffset() - this.xOffset,
                event.parentYOffset() - this.yOffset
        );
        return super.handleRenderTooltipsEvent(newData);
    }
    @Override
    public boolean handleRenderDebugEvent(IRenderDebugEvent.Data event) {
        IRenderDebugEvent.Data newData = new IRenderDebugEvent.Data(
                event.guiGraphics(),
                event.partialTick(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.parentXOffset() - this.xOffset,
                event.parentYOffset() - this.yOffset,
                event.info()
        );
        return super.handleRenderDebugEvent(newData);
    }

    @Override
    protected void onRenderBackground(IRenderBackgroundEvent.Data event) {
        super.onRenderBackground(event);
        event.guiGraphics().fill(rect().getX() + event.parentXOffset(), rect().getY() + event.parentYOffset(), rect().getX() + rect().getWidth() + event.parentXOffset(), rect().getY() + rect().getHeight() + event.parentYOffset(), 0xFF333333);
    }


    public void addElements(List<FlexWidget> widgets, boolean update){
        allItems.addAll(widgets);
        for (FlexWidget child : widgets){
            contentWidget.addChild(child);
        }
        if (update){
            update();
        }
    }
    public void addElement(FlexWidget widget, boolean update){
        allItems.add(widget);
        contentWidget.addChild(widget);
        if (update){
            update();
        }
    }
    public void removeElements(List<FlexWidget> widgets, boolean update){
        allItems.removeAll(widgets);
        for (FlexWidget child : widgets){
            contentWidget.removeChild(child);
        }
        if (update){
            update();
        }
    }
    public void removeElement(FlexWidget widget, boolean update){
        allItems.remove(widget);
        contentWidget.removeChild(widget);
        if (update){
            update();
        }
    }

    public void update(){
        search(currentSearchData);
        this.yOffset = 0;
        this.xOffset = 0;
    }

    public void search(String searchData){
        currentSearchData = searchData;
        for (IFlexWidget child : contentWidget.children()){
            child.hide();
        }
        allItems.stream()
                .filter(a -> a instanceof IScrollingViewSearchable)
                .map(a -> (IScrollingViewSearchable)a)
                .filter(a -> a.onSearch(currentSearchData))
                .map(a -> (FlexWidget)a)
                .forEach(FlexWidget::show);
        this.yOffset = 0;
        this.xOffset = 0;
    }


    private boolean dragging;
    private int dragStartY;
    private int dragStartX;
    private int dragStartYOffset;
    private int dragStartXOffset;

    @Override
    public boolean handleMouseClickedEvent(IMouseClickedEvent.Data event, boolean consumed) {
        IMouseClickedEvent.Data newData = new IMouseClickedEvent.Data(
                event.button(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset
        );
        return super.handleMouseClickedEvent(newData, consumed);
    }
    @Override
    public boolean handleMouseReleasedEvent(IMouseReleasedEvent.Data event, boolean consumed) {
        IMouseReleasedEvent.Data newData = new IMouseReleasedEvent.Data(
                event.button(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset
        );
        return super.handleMouseReleasedEvent(newData, consumed);
    }
    @Override
    public boolean handleMouseScrolledEvent(IMouseScrolledEvent.Data event, boolean consumed) {
        IMouseScrolledEvent.Data newData = new IMouseScrolledEvent.Data(
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.scrollX(),
                event.scrollY()
        );
        return super.handleMouseScrolledEvent(newData, consumed);
    }
    @Override
    public boolean handleMouseDraggedEvent(IMouseDraggedEvent.Data event, boolean consumed) {
        IMouseDraggedEvent.Data newData = new IMouseDraggedEvent.Data(
                event.button(),
                event.mouseX() + this.xOffset,
                event.mouseY() + this.yOffset,
                event.dragX(),
                event.dragY()
        );
        return super.handleMouseDraggedEvent(newData, consumed);
    }

    protected boolean onMouseClickedUnconsumed(IMouseClickedEvent.Data event){
        if (!rect().contains(event.mouseX(), event.mouseY())) return false;

        dragging = true;
        dragStartX = (int)event.mouseX();
        dragStartY = (int)event.mouseY();
        dragStartXOffset = xOffset;
        dragStartYOffset = yOffset;
        return true;
    }
    protected boolean onMouseReleasedUnconsumed(IMouseReleasedEvent.Data event) {
        dragging = false;
        dragStartY = 0;
        dragStartX = 0;
        dragStartYOffset = 0;
        dragStartXOffset = 0;
        return false;
    }
    protected boolean onMouseScrolledUnconsumed(IMouseScrolledEvent.Data event) {
        if (!rect().contains(event.mouseX(), event.mouseY())) return false;

        if (event.scrollX() != 0) xOffset += event.scrollX() > 0 ? -scrollXSensibility : scrollXSensibility;
        if (event.scrollY() != 0) yOffset += event.scrollY() > 0 ? -scrollYSensibility : scrollYSensibility;
        clampScroll();

        return true;
    }
    protected boolean onMouseDraggedUnconsumed(IMouseDraggedEvent.Data event){
        if (dragging && event.button() == 0){
            int dx = (int)event.mouseX() - dragStartX;
            int dy = (int)event.mouseY() - dragStartY;
            yOffset = dragStartYOffset - dy;
            xOffset = dragStartXOffset - dx;
            clampScroll();
            return true;
        }
        return false;
    }
    protected void onMouseClickedConsumed(IMouseClickedEvent.Data event) {
        dragging = false;
        dragStartY = 0;
        dragStartX = 0;
        dragStartYOffset = 0;
        dragStartXOffset = 0;
    }

    protected void clampScroll(){
        FlexRect contentRect = rect().getContentRect();
        switch (scrollingDirection){
            case Vertical :
                xOffset = 0;
                yOffset = Math.clamp(yOffset, 0, Math.max(0, contentWidget.rect().getHeight() - contentRect.getHeight()));
                break;
            case Horizontal:
                xOffset = Math.clamp(xOffset, 0, Math.max(0, contentWidget.rect().getWidth() - contentRect.getWidth()));
                yOffset = 0;
                break;
            case Both:
                xOffset = Math.clamp(xOffset, 0, Math.max(0, contentWidget.rect().getWidth() - contentRect.getWidth()));
                yOffset = Math.clamp(yOffset, 0, Math.max(0, contentWidget.rect().getHeight() - contentRect.getHeight()));
                break;
        }
    }
}
