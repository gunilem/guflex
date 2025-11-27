package guni.guflex.common.style.computer;

import guni.guflex.api.event.widgetEvents.IWidgetPositionedEvent;
import guni.guflex.api.event.widgetEvents.IWidgetUpdatedEvent;
import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.IStyleComputer;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.api.style.Style;
import guni.guflex.common.style.EnumStyle;
import guni.guflex.common.style.LengthPercentStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class PositionComputer  implements IStyleComputer {
    @Override
    public void compute(FlexRect parentContentRect, IFlexWidget widget) {
        computeLayoutGroup(widget);
    }


    public void computeAnchor(FlexRect parentContentRect, IFlexWidget widget) {
        String anchorData = (String) widget.style(Style.ANCHOR).getValue();

        if(parentContentRect == null) return;

        String positionStyle = (String)widget.style(Style.POSITION).getValue();
        int x = (Integer) widget.style(Style.X).getValue();
        int y = (Integer) widget.style(Style.Y).getValue();

        switch (anchorData){
            case(Style.TOP_LEFT) -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(x);
                    widget.rect().setY(y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x);
                widget.rect().setY(parentContentRect.getY() + y);
            }
            case(Style.TOP_RIGHT) -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width + x);
                    widget.rect().setY(y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth());
                widget.rect().setY(parentContentRect.getY() + y);
            }
            case(Style.BOTTOM_LEFT)  -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(x);
                    widget.rect().setY(Minecraft.getInstance().screen.height + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight());
            }
            case(Style.BOTTOM_RIGHT) -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth());
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight());
            }
            case(Style.LEFT_CENTER)  -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(x);
                    widget.rect().setY(Minecraft.getInstance().screen.height / 2 + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight() / 2);
            }
            case(Style.RIGHT_CENTER) -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height / 2 + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth());
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight() / 2);
            }
            case(Style.TOP_CENTER)  -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width / 2 + x);
                    widget.rect().setY(y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth() / 2);
                widget.rect().setY(parentContentRect.getY() + y);
            }
            case(Style.BOTTOM_CENTER) -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width / 2 + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth() / 2);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight());
            }
            case(Style.CENTER) -> {
                if (positionStyle.equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width / 2 + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height / 2 + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth() / 2);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight() / 2);
            }
        }
    }
    public void computePivot(IFlexWidget widget) {
        String pivotData = (String) widget.style(Style.PIVOT).getValue();

        widget.rect().setPivot(pivotData);
    }
    public void computeLayoutGroup(IFlexWidget widget) {
        String flexDirection = (String) widget.style(Style.FLEX_DIRECTION).getValue();
        IStyleSpec flexLayout = widget.style(Style.FLEX_LAYOUT);
        FlexRect contentRect = widget.rect().getContentRect();

        List<IFlexWidget> children = widget.children().stream().filter(IFlexWidget::displayed).sorted(
                (a, b) ->
                        ((Integer)b.style(Style.LAYOUT_PRIORITY).getValue()).compareTo((Integer)a.style(Style.LAYOUT_PRIORITY).getValue())
        ).toList();

        for (var child : children) {
            computePivot(child);
            computeAnchor(contentRect, child);
        }

        int positionX = contentRect.getX();
        int positionY = contentRect.getY();

        boolean isGrid = flexLayout != null && flexLayout.getValue() == Style.GRID;

        int itemSpacingX = 0;
        int itemSpacingY = 0;
        IStyleSpec itemSpacingXStyle = widget.style(Style.ITEM_SPACING);
        IStyleSpec itemSpacingYStyle = widget.style(Style.ITEM_SPACING);
        if (itemSpacingXStyle != null){ itemSpacingX = ((LengthPercentStyle.Data)itemSpacingXStyle.getValue()).resolve(contentRect.getWidth()); }
        if (itemSpacingYStyle != null){ itemSpacingY = ((LengthPercentStyle.Data)itemSpacingYStyle.getValue()).resolve(contentRect.getHeight()); }

        switch (flexDirection) {
            case Style.VERTICAL -> {
                if (isGrid) {
                    for (var child : children) {
                        String positionStyle = (String)child.style(Style.POSITION).getValue();
                        if (!positionStyle.equals(Style.FLEX)) continue;

                        FlexRect childMargin = child.rect().getMarginRect();
                        if (positionX + childMargin.getWidth() + itemSpacingX <= contentRect.getX() + contentRect.getWidth()) {
                            child.rect().setX(positionX);
                            child.rect().setY(positionY);
                            positionX += childMargin.getWidth() + itemSpacingX;
                        } else {
                            positionX += contentRect.getX();
                            positionY += childMargin.getHeight() + itemSpacingY;
                            child.rect().setX(positionX);
                            child.rect().setY(positionY);
                            positionX += childMargin.getWidth() + itemSpacingX;
                        }
                    }
                } else {
                    for (var child : children) {
                        String positionStyle = (String)child.style(Style.POSITION).getValue();
                        if (!positionStyle.equals(Style.FLEX)) continue;

                        child.rect().setY(positionY);
                        positionY += child.rect().getMarginRect().getHeight() + itemSpacingY;
                    }
                }
            }
            case Style.HORIZONTAL -> {
                if (isGrid) {
                    for (var child : children) {
                        String positionStyle = (String)child.style(Style.POSITION).getValue();
                        if (!positionStyle.equals(Style.FLEX)) continue;

                        FlexRect childMargin = child.rect().getMarginRect();
                        if (positionY + childMargin.getHeight() + itemSpacingY <= contentRect.getY() + contentRect.getHeight()) {
                            child.rect().setX(positionX);
                            child.rect().setY(positionY);
                            positionY += childMargin.getHeight() + itemSpacingY;
                        } else {
                            positionX += childMargin.getWidth() + itemSpacingX;
                            positionY += contentRect.getY();
                            child.rect().setX(positionX);
                            child.rect().setY(positionY);
                            positionY += childMargin.getHeight() + itemSpacingY;
                        }
                    }
                }
                else {
                    for (var child : children) {
                        String positionStyle = (String)child.style(Style.POSITION).getValue();
                        if (!positionStyle.equals(Style.FLEX)) continue;

                        child.rect().setX(positionX);
                        positionX += child.rect().getMarginRect().getWidth() + itemSpacingX;
                    }
                }
            }
        }
        for (var child : children) {
            computeRelativePosition(contentRect, child);
            computeLayoutGroup(child);
            child.handleUpdatedEvent();
            child.handlePositionedEvent();
        }
    }
    public void computeRelativePosition(FlexRect parentContentRect, IFlexWidget widget) {
        IStyleSpec leftStyle = widget.style(Style.LEFT);
        IStyleSpec rightStyle = widget.style(Style.RIGHT);
        IStyleSpec topStyle = widget.style(Style.TOP);
        IStyleSpec bottomStyle = widget.style(Style.BOTTOM);
        if (leftStyle != null) {
            widget.rect().addToX(((LengthPercentStyle.Data)leftStyle.getValue()).resolve(parentContentRect.getWidth()));
        }
        if (rightStyle != null) {
            widget.rect().addToX(-((LengthPercentStyle.Data)rightStyle.getValue()).resolve(parentContentRect.getWidth()));
        }
        if (topStyle != null) {
            widget.rect().addToY(((LengthPercentStyle.Data)topStyle.getValue()).resolve(parentContentRect.getWidth()));
        }
        if (bottomStyle != null) {
            widget.rect().addToY(-((LengthPercentStyle.Data)bottomStyle.getValue()).resolve(parentContentRect.getWidth()));
        }
    }
}
