package guni.guflex.common.style.computer;

import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.Style;
import guni.guflex.common.style.LengthPercentEnumStyle;
import guni.guflex.common.style.LengthPercentStyle;

public final class SizeComputer {
    private SizeComputer() {
        throw new AssertionError("Cannot instantiate SizeComputer");
    }

    public static void measure(FlexRect parentContentRect, IFlexWidget widget){
        FlexRect contentRect = widget.rect().getContentRect();
        for (var child : widget.children()){
            measureFixedSize(contentRect, child);
        }
        computeFlexWrap(widget);
        computeFlexAuto(widget);
    }


    public static void measureFixedSize(FlexRect parentContentRect, IFlexWidget widget) {
        measureMargin(parentContentRect, widget);
        measurePadding(parentContentRect, widget);

        LengthPercentEnumStyle widthData = widget.getStyle().getWidth();
        LengthPercentEnumStyle heightData = widget.getStyle().getHeight();

        widget.rect().setCropping(
                widget.getStyle().getLeft().resolve(parentContentRect.getWidth()),
                widget.getStyle().getRight().resolve(parentContentRect.getWidth()),
                widget.getStyle().getTop().resolve(parentContentRect.getHeight()),
                widget.getStyle().getBottom().resolve(parentContentRect.getHeight())
        );

        measureClampSize(parentContentRect, widget);

        if (widthData.getType() != LengthPercentEnumStyle.Type.Keyword) {
            int width = widthData.resolve(parentContentRect.getWidth());
            switch (widget.getStyle().getBoxSizingX()) {
                case Style.CONTENT_BOX -> widget.rect().setContentWidth(width);
                case Style.BORDER_BOX -> widget.rect().setWidth(width);
                case Style.MARGIN_BOX -> widget.rect().setMarginWidth(width);
            }
        }
        if (heightData.getType() != LengthPercentEnumStyle.Type.Keyword) {
            int height = heightData.resolve(parentContentRect.getHeight());
            switch (widget.getStyle().getBoxSizingY()) {
                case Style.CONTENT_BOX -> widget.rect().setContentHeight(height);
                case Style.BORDER_BOX -> widget.rect().setHeight(height);
                case Style.MARGIN_BOX -> widget.rect().setMarginHeight(height);
            }
        }

        FlexRect contentRect = widget.rect().getContentRect();
        for (var child : widget.children()){
            measureFixedSize(contentRect, child);
        }
    }


    private static void measureMargin(FlexRect parentContentRect, IFlexWidget widget){
        widget.rect().setMargin(
                widget.getStyle().getMarginLeft().resolve(parentContentRect.getWidth()),
                widget.getStyle().getMarginRight().resolve(parentContentRect.getWidth()),
                widget.getStyle().getMarginTop().resolve(parentContentRect.getHeight()),
                widget.getStyle().getMarginBottom().resolve(parentContentRect.getHeight())
        );
    }
    private static void measurePadding(FlexRect parentContentRect, IFlexWidget widget){
        widget.rect().setPadding(
                widget.getStyle().getPaddingLeft().resolve(parentContentRect.getWidth()),
                widget.getStyle().getPaddingRight().resolve(parentContentRect.getWidth()),
                widget.getStyle().getPaddingTop().resolve(parentContentRect.getHeight()),
                widget.getStyle().getPaddingBottom().resolve(parentContentRect.getHeight())
        );
    }
    private static void measureClampSize(FlexRect parentContentRect, IFlexWidget widget){
        widget.rect().setMinSize(
                widget.getStyle().getMinWidth().resolve(parentContentRect.getWidth()),
                widget.getStyle().getMinHeight().resolve(parentContentRect.getHeight())
        );
        widget.rect().setMaxSize(
                widget.getStyle().getMaxWidth().resolve(parentContentRect.getWidth()),
                widget.getStyle().getMaxHeight().resolve(parentContentRect.getHeight())
        );
    }

    private static void computeFlexWrap(IFlexWidget widget){
        int occupiedHeight = 0;
        int occupiedWidth = 0;
        int maxWidth = 0;
        int maxHeight = 0;

        FlexRect contentRect = widget.rect().getContentRect();

        String flexDirection = widget.getStyle().getFlexDirection();

        LengthPercentStyle itemSpacingStyle = widget.getStyle().getItemSpacing();

        for (IFlexWidget child : widget.children()) {
            if (!child.displayed()) continue;
            computeFlexWrap(child);

            if (!child.getStyle().getPosition().equals(Style.FLEX)) continue;

            FlexRect rect = child.rect().getMarginRect();
            occupiedWidth += rect.getWidth();
            occupiedHeight += rect.getHeight();
            maxWidth = Math.max(maxWidth, rect.getWidth());
            maxHeight = Math.max(maxHeight, rect.getHeight());

            if (flexDirection.equals(Style.HORIZONTAL)){
                occupiedWidth += itemSpacingStyle.resolve(contentRect.getWidth());
            } else {
                occupiedHeight += itemSpacingStyle.resolve(contentRect.getHeight());
            }
        }

        if (widget.getStyle().getWidth().isKeyword(Style.WRAP)) {
            if (flexDirection.equals(Style.HORIZONTAL)){
                switch (widget.getStyle().getBoxSizingX()){
                    case Style.CONTENT_BOX -> widget.rect().setContentWidth(occupiedWidth);
                    case Style.BORDER_BOX -> widget.rect().setWidth(occupiedWidth);
                    case Style.MARGIN_BOX -> widget.rect().setMarginWidth(occupiedWidth);
                }
            } else {
                switch (widget.getStyle().getBoxSizingX()){
                    case Style.CONTENT_BOX -> widget.rect().setContentWidth(maxWidth);
                    case Style.BORDER_BOX -> widget.rect().setWidth(maxWidth);
                    case Style.MARGIN_BOX -> widget.rect().setMarginWidth(maxWidth);
                }
            }
        }
        if (widget.getStyle().getHeight().isKeyword(Style.WRAP)) {
            if (flexDirection.equals(Style.VERTICAL)){
                switch (widget.getStyle().getBoxSizingY()){
                    case Style.CONTENT_BOX -> widget.rect().setContentHeight(occupiedHeight);
                    case Style.BORDER_BOX -> widget.rect().setHeight(occupiedHeight);
                    case Style.MARGIN_BOX -> widget.rect().setMarginHeight(occupiedHeight);
                }
            } else {
                switch (widget.getStyle().getBoxSizingY()){
                    case Style.CONTENT_BOX -> widget.rect().setContentHeight(maxHeight);
                    case Style.BORDER_BOX -> widget.rect().setHeight(maxHeight);
                    case Style.MARGIN_BOX -> widget.rect().setMarginHeight(maxHeight);
                }
            }
        }
    }
    private static void computeFlexAuto(IFlexWidget widget){
        int occupiedHeight = 0;
        int occupiedWidth = 0;
        int totalGrow = 0;

        FlexRect contentRect = widget.rect().getContentRect();
        String flexDirection = widget.getStyle().getFlexDirection();
        LengthPercentStyle itemSpacingStyle = widget.getStyle().getItemSpacing();

        for (IFlexWidget child : widget.children()) {
            if (!child.displayed()) continue;
            if (!child.getStyle().getPosition().equals(Style.FLEX)) continue;

            FlexRect rect = child.rect().getMarginRect();
            if (!child.getStyle().getWidth().isKeyword(Style.AUTO)) {
                occupiedWidth += rect.getWidth();
            }
            if (!child.getStyle().getHeight().isKeyword(Style.AUTO)) {
                occupiedHeight += rect.getHeight();
            }

            totalGrow += child.getStyle().getFlexGrow();

            if (flexDirection.equals(Style.HORIZONTAL)){
                occupiedWidth += itemSpacingStyle.resolve(contentRect.getWidth());
            } else {
                occupiedHeight += itemSpacingStyle.resolve(contentRect.getHeight());
            }
        }


        for (IFlexWidget child : widget.children()) {
            if (!child.displayed()) continue;

            if (child.getStyle().getFlexGrow() <= 0) {
                computeFlexAuto(child);
                child.handleMeasuredEvent();
                continue;
            }

            LengthPercentEnumStyle childWidthData = child.getStyle().getWidth();
            LengthPercentEnumStyle childHeightData = child.getStyle().getHeight();

            if (childWidthData.isKeyword(Style.AUTO)) {
                int widgetWidth = (contentRect.getWidth() - occupiedWidth) * (child.getStyle().getFlexGrow() / totalGrow);
                switch (child.getStyle().getBoxSizingX()) {
                    case Style.CONTENT_BOX -> child.rect().setContentWidth(widgetWidth);
                    case Style.BORDER_BOX -> child.rect().setWidth(widgetWidth);
                    case Style.MARGIN_BOX -> child.rect().setMarginWidth(widgetWidth);
                }
            }
            else {
                int width = childWidthData.resolve(contentRect.getWidth());
                if (width >= 0) {
                    switch (child.getStyle().getBoxSizingX()) {
                        case Style.CONTENT_BOX -> child.rect().setContentWidth(width);
                        case Style.BORDER_BOX -> child.rect().setWidth(width);
                        case Style.MARGIN_BOX -> child.rect().setMarginWidth(width);
                    }
                }
            }
            if (childHeightData.isKeyword(Style.AUTO)) {
                int widgetHeight = (contentRect.getHeight() - occupiedHeight) * (child.getStyle().getFlexGrow() / totalGrow);
                switch (child.getStyle().getBoxSizingY()) {
                    case Style.CONTENT_BOX -> child.rect().setContentHeight(widgetHeight);
                    case Style.BORDER_BOX -> child.rect().setHeight(widgetHeight);
                    case Style.MARGIN_BOX -> child.rect().setMarginHeight(widgetHeight);
                }
            }
            else {
                int height = childHeightData.resolve(contentRect.getHeight());
                if (height >= 0) {
                    switch (child.getStyle().getBoxSizingY()) {
                        case Style.CONTENT_BOX -> child.rect().setContentHeight(height);
                        case Style.BORDER_BOX -> child.rect().setHeight(height);
                        case Style.MARGIN_BOX -> child.rect().setMarginHeight(height);
                    }
                }
            }

            computeFlexAuto(child);
            child.handleMeasuredEvent();
        }
    }
}
