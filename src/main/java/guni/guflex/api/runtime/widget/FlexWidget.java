package guni.guflex.api.runtime.widget;

import guni.guflex.api.event.FlexWidgetEventHandler;
import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.screenEvents.*;
import guni.guflex.api.event.widgetEvents.*;
import guni.guflex.api.runtime.drawable.IDrawable;
import guni.guflex.api.runtime.screen.IFlexScreen;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.FlexStyle;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.core.registers.Internals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.*;

public class FlexWidget implements IFlexWidget {
    protected String name;

    protected FlexWidgetEventHandler eventHandler;
    protected FlexRect bounds;
    protected FlexStyle style;
    protected List<IFlexWidget> children;
    protected boolean displayed = true;
    protected boolean displayedInHierarchy = true;
    protected IDrawable background;

    protected List<Component> tooltips = new ArrayList<>();

    public FlexWidget(){
        eventHandler = new FlexWidgetEventHandler();
        bounds = FlexRect.EMPTY();
        style = new FlexStyle(eventHandler);
        children = new ArrayList<>();

        eventHandler.render.background.register(this::onRenderBackground);
        eventHandler.render.debug.register(this::onRenderDebug);
        eventHandler.render.tooltips.register(this::onRenderTooltips);
    }

    protected void onRenderBackground(IRenderBackgroundEvent.Data data) {
        if (background != null){
            background.draw(data.guiGraphics(), rect());
        }
    }

    protected boolean onRenderDebug(IRenderDebugEvent.Data event) {
        if (event.info().debugTrigger != IRenderDebugEvent.DebugInfo.DebugTrigger.Always && !this.rect().contains(event.mouseX(), event.mouseY())) {
            return false;
        } else {
            if (event.info().marginDebug) {
                FlexRect marginRect = this.rect().getMarginRect();
                int marginMinX = marginRect.getX() + event.parentXOffset();
                int marginMaxX = marginRect.getX() + marginRect.getWidth() + event.parentXOffset();
                int marginMinY = marginRect.getY() + event.parentYOffset();
                int marginMaxY = marginRect.getY() + marginRect.getHeight() + event.parentYOffset();
                event.guiGraphics().fill(marginMinX, marginMinY, marginMaxX, marginMinY + 1, 0xFF00FF00);
                event.guiGraphics().fill(marginMinX, marginMaxY, marginMaxX, marginMaxY + 1, 0xFF00FF00);
                event.guiGraphics().fill(marginMinX, marginMinY, marginMinX + 1, marginMaxY, 0xFF00FF00);
                event.guiGraphics().fill(marginMaxX, marginMinY, marginMaxX + 1, marginMaxY, 0xFF00FF00);
            }

            if (event.info().borderDebug) {
                int borderMinX = this.bounds.getX() + event.parentXOffset();
                int borderMaxX = this.bounds.getX() + this.bounds.getWidth() + event.parentXOffset();
                int borderMinY = this.bounds.getY() + event.parentYOffset();
                int borderMaxY = this.bounds.getY() + this.bounds.getHeight() + event.parentYOffset();
                event.guiGraphics().fill(borderMinX, borderMinY, borderMaxX, borderMinY + 1, 0xFF000000);
                event.guiGraphics().fill(borderMinX, borderMaxY, borderMaxX, borderMaxY + 1, 0xFF000000);
                event.guiGraphics().fill(borderMinX, borderMinY, borderMinX + 1, borderMaxY, 0xFF000000);
                event.guiGraphics().fill(borderMaxX, borderMinY, borderMaxX + 1, borderMaxY, 0xFF000000);
            }

            if (event.info().paddingDebug) {
                FlexRect contentRect = this.rect().getContentRect();
                int paddingMinX = contentRect.getX() + event.parentXOffset();
                int paddingMaxX = contentRect.getX() + contentRect.getWidth() + event.parentXOffset();
                int paddingMinY = contentRect.getY() + event.parentYOffset();
                int paddingMaxY = contentRect.getY() + contentRect.getHeight() + event.parentYOffset();
                event.guiGraphics().fill(paddingMinX, paddingMinY, paddingMaxX, paddingMinY + 1, 0xFF0000FF);
                event.guiGraphics().fill(paddingMinX, paddingMaxY, paddingMaxX, paddingMaxY + 1, 0xFF0000FF);
                event.guiGraphics().fill(paddingMinX, paddingMinY, paddingMinX + 1, paddingMaxY, 0xFF0000FF);
                event.guiGraphics().fill(paddingMaxX, paddingMinY, paddingMaxX + 1, paddingMaxY, 0xFF0000FF);
            }

            event.guiGraphics().pose().translate(0, 0, 1500);
            if (event.info().displayDebugBox) {
                Font font = Minecraft.getInstance().font;

                int baseX = 10;
                int baseY = 10;
                int baseW = 130;
                int baseH = 130 + 10 * 3;

                Internals.getTextures().button_background.draw(event.guiGraphics(), baseX - 5, baseY - 5, baseW + 5, baseH + 5);

                int minX = baseX;
                int minY = baseY;
                int maxX = baseW;
                int maxY = baseH;

                event.guiGraphics().drawString(font, this.name, minX, minY, 0);
                minY += 10;
                event.guiGraphics().drawString(font, "x: " + this.rect().getX() + "  y: " + this.rect().getY(), minX, minY, 0);
                minY += 10;
                event.guiGraphics().drawString(font, "w: " + this.rect().getWidth() + "  h: " + this.rect().getHeight(), minX, minY, 0);
                minY += 10;
                FlexRect marginRect = this.rect().getMarginRect();


                //Margin Data
                int marginLeft = this.rect().getX() - marginRect.getX();
                int marginTop = this.rect().getY() - marginRect.getY();
                int marginRight = marginRect.getX() + marginRect.getWidth() - (this.rect().getX() + this.rect().getWidth());
                int marginBottom = marginRect.getY() + marginRect.getHeight() - (this.rect().getY() + this.rect().getHeight());
                int positionUpSide = minY + (maxY - minY) / 2 - font.lineHeight / 2;

                event.guiGraphics().drawString(font, "Margin", minX + 2, minY + 2, 0x00FF00);
                event.guiGraphics().drawString(font, marginLeft + "", minX + 2, positionUpSide, 65280);
                event.guiGraphics().drawString(font, marginRight + "", maxX - 2 - font.width("" + marginRight), positionUpSide, 65280);
                event.guiGraphics().drawString(font, marginTop + "", minX + (maxX - minX) / 2 - font.width("" + marginTop) / 2, minY + 2, 65280);
                event.guiGraphics().drawString(font, marginBottom + "", minX + (maxX - minX) / 2 - font.width("" + marginBottom) / 2, maxY - 2 - 9, 65280);

                //Margin Square
                event.guiGraphics().fill(minX, minY, maxX, minY + 1, 0xFF00FF00);
                event.guiGraphics().fill(minX, maxY, maxX, maxY + 1, 0xFF00FF00);
                event.guiGraphics().fill(minX, minY, minX + 1, maxY, 0xFF00FF00);
                event.guiGraphics().fill(maxX, minY, maxX + 1, maxY, 0xFF00FF00);

                minX += font.lineHeight + 4;
                minY += font.lineHeight + 4;
                maxX -= font.lineHeight + 4;
                maxY -= font.lineHeight + 4;

                //Border Square
                event.guiGraphics().fill(minX, minY, maxX, minY + 1, 0xFF000000);
                event.guiGraphics().fill(minX, maxY, maxX, maxY + 1, 0xFF000000);
                event.guiGraphics().fill(minX, minY, minX + 1, maxY, 0xFF000000);
                event.guiGraphics().fill(maxX, minY, maxX + 1, maxY, 0xFF000000);

                //Padding Data
                FlexRect paddingRect = this.rect().getContentRect();
                int paddingLeft = paddingRect.getX() - this.rect().getX();
                int paddingRight = paddingRect.getY() - this.rect().getY();
                int paddingTop = this.rect().getX() + this.rect().getWidth() - (paddingRect.getX() + paddingRect.getWidth());
                int paddingBottom = this.rect().getY() + this.rect().getHeight() - (paddingRect.getY() + paddingRect.getHeight());

                event.guiGraphics().drawString(font, "Padding", minX + 2, minY + 2, 0xFF0000FF);
                event.guiGraphics().drawString(font, paddingLeft + "", minX + 2, positionUpSide, 0xFF0000FF);
                event.guiGraphics().drawString(font, paddingRight + "", maxX - 2 - font.width("" + marginRight), positionUpSide, 0xFF0000FF);
                event.guiGraphics().drawString(font, paddingTop + "", minX + (maxX - minX) / 2 - font.width("" + paddingTop) / 2, minY + 2, 0xFF0000FF);
                event.guiGraphics().drawString(font, paddingBottom + "", minX + (maxX - minX) / 2 - font.width("" + paddingBottom) / 2, maxY - 2 - 9, 0xFF0000FF);

                minX += font.lineHeight + 4;
                minY += font.lineHeight + 4;
                maxX -= font.lineHeight + 4;
                maxY -= font.lineHeight + 4;

                //Padding Square
                event.guiGraphics().fill(minX, minY, maxX, minY + 1, 0xFF0000FF);
                event.guiGraphics().fill(minX, maxY, maxX, maxY + 1, 0xFF0000FF);
                event.guiGraphics().fill(minX, minY, minX + 1, maxY, 0xFF0000FF);
                event.guiGraphics().fill(maxX, minY, maxX + 1, maxY, 0xFF0000FF);
            }
            event.guiGraphics().pose().translate(0, 0, -1500);

            return event.info().debugTrigger != IRenderDebugEvent.DebugInfo.DebugTrigger.Always;
        }
    }

    protected boolean onRenderTooltips(IRenderTooltipsEvent.Data event){
        if (tooltips.isEmpty()) return false;
        if (!rect().contains(event.mouseX(), event.mouseY())) return false;

        event.guiGraphics().renderTooltip(Minecraft.getInstance().font, tooltips, Optional.empty(), event.mouseX(), event.mouseY());

        return true;
    }

    public void setBackground(IDrawable background){
        this.background = background;
    }

    public void defineName(String name){
        this.name = name;
    }

    @Override
    public FlexRect rect() { return bounds; }

    @Override
    public FlexRect resetRect() {
        return bounds = FlexRect.EMPTY();
    }

    @Override
    public FlexStyle getStyle() {
        return style;
    }

    @Override
    public List<IFlexWidget> children() {
        return children;
    }

    @Override
    public FlexWidgetEventHandler eventHandler() {
        return eventHandler;
    }

    @Override
    public boolean displayed() { return displayed && displayedInHierarchy; }
    @Override
    public boolean renderable() { return rect().renderable() && displayed() ; }

    @Override
    public void show() { displayed = true; setHierarchyDisplayedProperty(true); handleShownEvent(); style.setDirty(); }
    @Override
    public void hide() { displayed = false; setHierarchyDisplayedProperty(false); handleHiddenEvent();  style.setDirty(); }

    @Override
    public void addChild(IFlexWidget child) {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen) screen.addDelayOperation(() -> {
            children.add(child);
            handleChildAddedEvent(new IWidgetChildAddedEvent.Data(child));
        });
        style.setDirty();
    }
    @Override
    public void removeChild(IFlexWidget child) {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen) screen.addDelayOperation(() -> {
            children.remove(child);
            handleChildRemovedEvent(new IWidgetChildRemovedEvent.Data(child));
        });
        style.setDirty();
    }
    @Override
    public void removeAllChild(){
        for (int i = children.size() - 1; i >= 0; i--){
            removeChild(children.get(i));
        }
    }

    @Override
    public void recomputeLayout(IFlexWidget parent){
        style.measure(parent, this);
        style.position(parent, this);
    }

    public void addTooltip(Component tooltip){
        this.tooltips.add(tooltip);
    }
    public void addTooltips(Collection<Component> tooltips){
        this.tooltips.addAll(tooltips);
    }
    public void clearTooltips(){
        this.tooltips.clear();
    }

    @Override
    public void setHierarchyDisplayedProperty(boolean value){
        displayedInHierarchy = value;
        for (IFlexWidget child : children){
            child.setHierarchyDisplayedProperty(value);
        }
    }

    public boolean checkDirty(){
        if (style.isDirty()) { return true; }
        for (IFlexWidget child : children){
            if (child.checkDirty()){
                if (style.dependsOnChildren()) return true;
                else child.recomputeLayout(this);
            }
        }
        return false;
    }

    public boolean handleMouseClickedEvent(IMouseClickedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleMouseClickedEvent(event, consumed);
        }
        eventHandler.mouse.clicked.always.invoke(event);
        if (consumed) eventHandler.mouse.clicked.consumed.invoke(event);
        else consumed = eventHandler.mouse.clicked.unconsumed.invoke(event);
        return consumed;
    }
    public boolean handleMouseReleasedEvent(IMouseReleasedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleMouseReleasedEvent(event, consumed);
        }
        eventHandler.mouse.released.always.invoke(event);
        if (consumed) eventHandler.mouse.released.consumed.invoke(event);
        else consumed = eventHandler.mouse.released.unconsumed.invoke(event);
        return consumed;
    }
    public boolean handleMouseScrolledEvent(IMouseScrolledEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleMouseScrolledEvent(event, consumed);
        }
        eventHandler.mouse.scrolled.always.invoke(event);
        if (consumed) eventHandler.mouse.scrolled.consumed.invoke(event);
        else consumed = eventHandler.mouse.scrolled.unconsumed.invoke(event);
        return consumed;
    }
    public boolean handleMouseDraggedEvent(IMouseDraggedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleMouseDraggedEvent(event, consumed);
        }
        eventHandler.mouse.dragged.always.invoke(event);
        if (consumed) eventHandler.mouse.dragged.consumed.invoke(event);
        else consumed = eventHandler.mouse.dragged.unconsumed.invoke(event);
        return consumed;
    }
    public boolean handleMouseMovedEvent(IMouseMovedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleMouseMovedEvent(event, consumed);
        }
        eventHandler.mouse.moved.always.invoke(event);
        if (consumed) eventHandler.mouse.moved.consumed.invoke(event);
        else consumed = eventHandler.mouse.moved.unconsumed.invoke(event);
        return consumed;
    }

    public boolean handleKeyPressedEvent(IKeyPressedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleKeyPressedEvent(event, consumed);
        }
        eventHandler.key.pressed.always.invoke(event);
        if (consumed) eventHandler.key.pressed.consumed.invoke(event);
        else consumed = eventHandler.key.pressed.unconsumed.invoke(event);
        return consumed;
    }
    public boolean handleKeyReleasedEvent(IKeyReleasedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleKeyReleasedEvent(event, consumed);
        }
        eventHandler.key.released.always.invoke(event);
        if (consumed) eventHandler.key.released.consumed.invoke(event);
        else consumed = eventHandler.key.released.unconsumed.invoke(event);
        return consumed;
    }
    public boolean handleCharTypedEvent(ICharTypedEvent.Data event, boolean consumed){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            consumed = child.handleCharTypedEvent(event, consumed);
        }
        eventHandler.key.typed.always.invoke(event);
        if (consumed) eventHandler.key.typed.consumed.invoke(event);
        else consumed = eventHandler.key.typed.unconsumed.invoke(event);
        return consumed;
    }



    public void handleRenderEvent(IRenderEvent.Data event){
        if (!renderable()) return;
        eventHandler.render.rendered.invoke(event);
        for (IFlexWidget child : children){
            child.handleRenderEvent(event);
        }
    }
    public void handleRenderBackgroundEvent(IRenderBackgroundEvent.Data event){
        if (!renderable()) return;
        eventHandler.render.background.invoke(event);
        for (IFlexWidget child : children){
            child.handleRenderBackgroundEvent(event);
        }
    }
    public void handleRenderForegroundEvent(IRenderForegroundEvent.Data event){
        if (!renderable()) return;
        eventHandler.render.foreground.invoke(event);
        for (IFlexWidget child : children){
            child.handleRenderForegroundEvent(event);
        }
    }
    public boolean handleRenderTooltipsEvent(IRenderTooltipsEvent.Data event){
        if (!renderable()) return false;
        for (IFlexWidget child : children){
            if (child.handleRenderTooltipsEvent(event)) return true;
        }
        return eventHandler.render.tooltips.invoke(event);
    }
    public boolean handleRenderDebugEvent(IRenderDebugEvent.Data event){
        if (!renderable()) return false;
        for (int i = children.size() - 1; i >= 0; i--){
            IFlexWidget child = children.get(i);
            if (child.handleRenderDebugEvent(event)  && event.info().debugTrigger == IRenderDebugEvent.DebugInfo.DebugTrigger.Hover) return true;
        }
        return eventHandler.render.debug.invoke(event);
    }

    public void handleShownEvent(){
        if (!displayed) return;
        eventHandler.widget.shown.invoke();
        for (IFlexWidget child : children){
            child.handleShownEvent();
        }
    }
    public void handleHiddenEvent(){
        if (displayed) return;
        eventHandler.widget.hidden.invoke();
        for (IFlexWidget child : children){
            child.handleHiddenEvent();
        }
    }

    public void handleAddedEvent(){
        eventHandler.widget.added.invoke();
        for (IFlexWidget child : children){
            child.handleAddedEvent();
        }
    }
    public void handleRemovedEvent(){
        eventHandler.widget.removed.invoke();
        for (IFlexWidget child : children){
            child.handleRemovedEvent();
        }
    }

    public void handleChildAddedEvent(IWidgetChildAddedEvent.Data event){
        eventHandler.widget.childAdded.invoke(event);
        event.widget().handleAddedEvent();
    }
    public void handleChildRemovedEvent(IWidgetChildRemovedEvent.Data event){
        eventHandler.widget.childRemoved.invoke(event);
        event.widget().handleRemovedEvent();
    }

    public void handleMeasuredEvent(){ eventHandler.widget.measured.invoke(); }
    public void handlePositionedEvent(){ eventHandler.widget.positioned.invoke(); }
    public void handleUpdatedEvent(){
        eventHandler.widget.updated.invoke();
        for (IFlexWidget child : children){
            child.handleUpdatedEvent();
        }
    }
}
