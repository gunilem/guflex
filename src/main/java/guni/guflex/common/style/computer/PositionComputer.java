package guni.guflex.common.style.computer;

import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.IStyleComputer;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.api.style.Style;
import guni.guflex.common.style.LengthPercentStyle;
import net.minecraft.client.Minecraft;

import java.util.List;

public class PositionComputer {
    private PositionComputer() {
        throw new AssertionError("Cannot instantiate PositionComputer");
    }

    public static void position(FlexRect parentContentRect, IFlexWidget widget) {
        computeLayoutGroup(widget);
    }


    public static void computeAnchor(FlexRect parentContentRect, IFlexWidget widget) {
        if(parentContentRect == null) return;

        int x = widget.getStyle().getX();
        int y = widget.getStyle().getY();

        switch (widget.getStyle().getAnchor()){
            case(Style.TOP_LEFT) -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(x);
                    widget.rect().setY(y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x);
                widget.rect().setY(parentContentRect.getY() + y);
            }
            case(Style.TOP_RIGHT) -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width + x);
                    widget.rect().setY(y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth());
                widget.rect().setY(parentContentRect.getY() + y);
            }
            case(Style.BOTTOM_LEFT)  -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(x);
                    widget.rect().setY(Minecraft.getInstance().screen.height + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight());
            }
            case(Style.BOTTOM_RIGHT) -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth());
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight());
            }
            case(Style.LEFT_CENTER)  -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(x);
                    widget.rect().setY(Minecraft.getInstance().screen.height / 2 + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight() / 2);
            }
            case(Style.RIGHT_CENTER) -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height / 2 + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth());
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight() / 2);
            }
            case(Style.TOP_CENTER)  -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width / 2 + x);
                    widget.rect().setY(y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth() / 2);
                widget.rect().setY(parentContentRect.getY() + y);
            }
            case(Style.BOTTOM_CENTER) -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width / 2 + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth() / 2);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight());
            }
            case(Style.CENTER) -> {
                if (widget.getStyle().getPosition().equals(Style.FIXED)){
                    widget.rect().setX(Minecraft.getInstance().screen.width / 2 + x);
                    widget.rect().setY(Minecraft.getInstance().screen.height / 2 + y);
                    break;
                }
                widget.rect().setX(parentContentRect.getX() + x + parentContentRect.getWidth() / 2);
                widget.rect().setY(parentContentRect.getY() + y + parentContentRect.getHeight() / 2);
            }
        }

        if (widget.rect().getX() + widget.rect().getWidth() > Minecraft.getInstance().screen.width){
            widget.rect().setX(Minecraft.getInstance().screen.width - widget.rect().getWidth());
        }
        else if (widget.rect().getX() < 0){
            widget.rect().setX(widget.rect().getWidth());
        }
        if (widget.rect().getY() + widget.rect().getHeight() > Minecraft.getInstance().screen.height){
            widget.rect().setY(Minecraft.getInstance().screen.height - widget.rect().getHeight());
        }
        else if (widget.rect().getY() < 0){
            widget.rect().setY(widget.rect().getHeight());
        }
    }

    public static void computePivot(IFlexWidget widget) {
        widget.rect().setPivot(widget.getStyle().getPivot());
    }

    public static void computeLayoutGroup(IFlexWidget widget) {
        FlexRect contentRect = widget.rect().getContentRect();

        List<IFlexWidget> children = widget.children().stream().filter(IFlexWidget::displayed).sorted(
                (obj1, obj2) ->
                        Integer.compare(obj2.getStyle().getLayoutPriority(), obj1.getStyle().getLayoutPriority())
        ).toList();

        for (var child : children) {
            computePivot(child);
            computeAnchor(contentRect, child);
        }

        int positionX = contentRect.getX();
        int positionY = contentRect.getY();

        int itemSpacingX = widget.getStyle().getItemSpacing().resolve(contentRect.getWidth());
        int itemSpacingY = widget.getStyle().getItemSpacing().resolve(contentRect.getHeight());

        switch (widget.getStyle().getFlexDirection()) {
            case Style.VERTICAL -> {
                if (widget.getStyle().getFlexLayout().equals(Style.GRID)) {
                    for (var child : children) {
                        if (!child.getStyle().getPosition().equals(Style.FLEX)) continue;

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
                        if (!child.getStyle().getPosition().equals(Style.FLEX)) continue;

                        child.rect().setY(positionY);
                        positionY += child.rect().getMarginRect().getHeight() + itemSpacingY;
                    }
                }
            }
            case Style.HORIZONTAL -> {
                if (widget.getStyle().getFlexLayout().equals(Style.GRID)) {
                    for (var child : children) {
                        if (!child.getStyle().getPosition().equals(Style.FLEX)) continue;

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
                        if (!child.getStyle().getPosition().equals(Style.FLEX)) continue;

                        child.rect().setX(positionX);
                        positionX += child.rect().getMarginRect().getWidth() + itemSpacingX;
                    }
                }
            }
        }
        for (var child : children) {
            computeLayoutGroup(child);
            child.handleUpdatedEvent();
            child.handlePositionedEvent();
        }
    }
}
