package guni.guflex.common.style.computer;

import guni.guflex.api.event.widgetEvents.IWidgetMeasuredEvent;
import guni.guflex.api.event.widgetEvents.IWidgetPositionedEvent;
import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.IStyleComputer;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.api.style.Style;
import guni.guflex.common.style.EnumStyle;
import guni.guflex.common.style.LengthPercentEnumStyle;
import guni.guflex.common.style.LengthPercentStyle;

public class SizeComputer implements IStyleComputer {
    @Override
    public void compute(FlexRect parentContentRect, IFlexWidget widget) {
        FlexRect contentRect = widget.rect().getContentRect();
        for (var child : widget.children()){
            computeFixedSize(contentRect, child);
        }
        computeFlexWrap(widget);
        computeFlexAuto(widget);
    }


    public void computeFixedSize(FlexRect parentContentRect, IFlexWidget widget) {
        computeMargin(parentContentRect, widget);
        computePadding(parentContentRect, widget);

        LengthPercentEnumStyle.Data widthData = (LengthPercentEnumStyle.Data) widget.style(Style.WIDTH).getValue();
        LengthPercentEnumStyle.Data heightData = (LengthPercentEnumStyle.Data) widget.style(Style.HEIGHT).getValue();

        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        IStyleSpec leftStyle = widget.style(Style.LEFT);
        IStyleSpec rightStyle = widget.style(Style.RIGHT);
        IStyleSpec topStyle = widget.style(Style.TOP);
        IStyleSpec bottomStyle = widget.style(Style.BOTTOM);
        if (leftStyle != null) {
            left = ((LengthPercentStyle.Data)leftStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (rightStyle != null) {
            right = -((LengthPercentStyle.Data)rightStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (topStyle != null) {
            top = ((LengthPercentStyle.Data)topStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (bottomStyle != null) {
            bottom = -((LengthPercentStyle.Data)bottomStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        widget.rect().setCropping(left, right, top, bottom);

        computeClampSize(parentContentRect, widget);
        String boxSizingX = (String)widget.style(Style.BOX_SIZING_X).getValue();
        String boxSizingY = (String)widget.style(Style.BOX_SIZING_Y).getValue();

        if (widthData != null) {
            int width = widthData.resolve(parentContentRect.getWidth());
            switch (boxSizingX){
                case Style.CONTENT_BOX -> widget.rect().setContentWidth(width);
                case Style.BORDER_BOX -> widget.rect().setWidth(width);
                case Style.MARGIN_BOX -> widget.rect().setMarginWidth(width);
            }
        }
        if (heightData != null) {
            int height = heightData.resolve(parentContentRect.getHeight());
            switch (boxSizingY) {
                case Style.CONTENT_BOX -> widget.rect().setContentHeight(height);
                case Style.BORDER_BOX -> widget.rect().setHeight(height);
                case Style.MARGIN_BOX -> widget.rect().setMarginHeight(height);
            }
        }

        FlexRect contentRect = widget.rect().getContentRect();
        for (var child : widget.children()){
            computeFixedSize(contentRect, child);
        }
    }


    private void computeMargin(FlexRect parentContentRect, IFlexWidget widget){
        IStyleSpec marginLeftStyle = widget.style(Style.MARGIN_LEFT);
        IStyleSpec marginRightStyle = widget.style(Style.MARGIN_RIGHT);
        IStyleSpec marginTopStyle = widget.style(Style.MARGIN_TOP);
        IStyleSpec marginBottomStyle = widget.style(Style.MARGIN_BOTTOM);

        int marginLeft = 0;
        int marginRight = 0;
        int marginTop = 0;
        int marginBottom = 0;

        if (marginLeftStyle != null) {
            marginLeft = ((LengthPercentStyle.Data)marginLeftStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (marginRightStyle != null) {
            marginRight = ((LengthPercentStyle.Data)marginRightStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (marginTopStyle != null) {
            marginTop = ((LengthPercentStyle.Data)marginTopStyle.getValue()).resolve(parentContentRect.getHeight());
        }
        if (marginBottomStyle != null) {
            marginBottom = ((LengthPercentStyle.Data)marginBottomStyle.getValue()).resolve(parentContentRect.getHeight());
        }

        widget.rect().setMargin(marginLeft, marginRight, marginTop, marginBottom);
    }
    private void computePadding(FlexRect parentContentRect, IFlexWidget widget){
        IStyleSpec paddingLeftStyle = widget.style(Style.PADDING_LEFT);
        IStyleSpec paddingRightStyle = widget.style(Style.PADDING_RIGHT);
        IStyleSpec paddingTopStyle = widget.style(Style.PADDING_TOP);
        IStyleSpec paddingBottomStyle = widget.style(Style.PADDING_BOTTOM);

        int paddingLeft = 0;
        int paddingRight = 0;
        int paddingTop = 0;
        int paddingBottom = 0;

        if (paddingLeftStyle != null) {
            paddingLeft = ((LengthPercentStyle.Data)paddingLeftStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (paddingRightStyle != null) {
            paddingRight = ((LengthPercentStyle.Data)paddingRightStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (paddingTopStyle != null) {
            paddingTop = ((LengthPercentStyle.Data)paddingTopStyle.getValue()).resolve(parentContentRect.getWidth());
        }
        if (paddingBottomStyle != null) {
            paddingBottom = ((LengthPercentStyle.Data)paddingBottomStyle.getValue()).resolve(parentContentRect.getWidth());
        }

        widget.rect().setPadding(paddingLeft, paddingRight, paddingTop, paddingBottom);
    }
    private void computeClampSize(FlexRect parentContentRect, IFlexWidget widget){
        Integer maxWidth = null;
        Integer maxHeight = null;
        Integer minWidth = null;
        Integer minHeight = null;

        IStyleSpec maxWidthStyle = widget.style(Style.MAX_WIDTH);
        if (maxWidthStyle != null) { maxWidth = ((LengthPercentStyle.Data)maxWidthStyle.getValue()).resolve(parentContentRect.getWidth()); }
        IStyleSpec maxHeightStyle = widget.style(Style.MAX_HEIGHT);
        if (maxHeightStyle != null) { maxHeight = ((LengthPercentStyle.Data)maxHeightStyle.getValue()).resolve(parentContentRect.getWidth()); }
        IStyleSpec minWidthStyle = widget.style(Style.MIN_WIDTH);
        if (minWidthStyle != null) { minWidth = ((LengthPercentStyle.Data)minWidthStyle.getValue()).resolve(parentContentRect.getWidth()); }
        IStyleSpec minHeightStyle = widget.style(Style.MIN_HEIGHT);
        if (minHeightStyle != null) { minHeight = ((LengthPercentStyle.Data)minHeightStyle.getValue()).resolve(parentContentRect.getWidth()); }

        widget.rect().setMinSize(minWidth, minHeight);
        widget.rect().setMaxSize(maxWidth, maxHeight);
    }

    private void computeFlexWrap(IFlexWidget widget){
        int occupiedHeight = 0;
        int occupiedWidth = 0;
        int maxWidth = 0;
        int maxHeight = 0;
        int itemSpacingX = 0;
        int itemSpacingY = 0;

        FlexRect contentRect = widget.rect().getContentRect();
        String flexDirection = (String)widget.style(Style.FLEX_DIRECTION).getValue();
        String boxSizingX = (String)widget.style(Style.BOX_SIZING_X).getValue();
        String boxSizingY = (String)widget.style(Style.BOX_SIZING_Y).getValue();
        LengthPercentEnumStyle.Data widthData = (LengthPercentEnumStyle.Data) widget.style(Style.WIDTH).getValue();
        LengthPercentEnumStyle.Data heightData = (LengthPercentEnumStyle.Data) widget.style(Style.HEIGHT).getValue();

        IStyleSpec itemSpacingStyle = widget.style(Style.ITEM_SPACING);
        if (itemSpacingStyle != null){
            itemSpacingX = ((LengthPercentStyle.Data)itemSpacingStyle.getValue()).resolve(contentRect.getWidth());
            itemSpacingY = ((LengthPercentStyle.Data)itemSpacingStyle.getValue()).resolve(contentRect.getHeight());
        }

        for (IFlexWidget child : widget.children()) {
            if (!child.displayed()) continue;
            computeFlexWrap(child);

            String positionStyle = (String)child.style(Style.POSITION).getValue();
            if (!positionStyle.equals(Style.FLEX)) continue;

            FlexRect rect = child.rect().getMarginRect();
            occupiedWidth += rect.getWidth();
            occupiedHeight += rect.getHeight();
            maxWidth = Math.max(maxWidth, rect.getWidth());
            maxHeight = Math.max(maxHeight, rect.getHeight());

            if (flexDirection.equals(Style.HORIZONTAL)){
                occupiedWidth += itemSpacingX;
            } else {
                occupiedHeight += itemSpacingY;
            }
        }

        if (widthData.isString(Style.WRAP)) {
            if (flexDirection.equals(Style.HORIZONTAL)){
                switch (boxSizingX){
                    case Style.CONTENT_BOX -> widget.rect().setContentWidth(occupiedWidth);
                    case Style.BORDER_BOX -> widget.rect().setWidth(occupiedWidth);
                    case Style.MARGIN_BOX -> widget.rect().setMarginWidth(occupiedWidth);
                }
            } else {
                switch (boxSizingX){
                    case Style.CONTENT_BOX -> widget.rect().setContentWidth(maxWidth);
                    case Style.BORDER_BOX -> widget.rect().setWidth(maxWidth);
                    case Style.MARGIN_BOX -> widget.rect().setMarginWidth(maxWidth);
                }
            }
        }
        if (heightData.isString(Style.WRAP)) {
            if (flexDirection.equals(Style.VERTICAL)){
                switch (boxSizingY){
                    case Style.CONTENT_BOX -> widget.rect().setContentHeight(occupiedHeight);
                    case Style.BORDER_BOX -> widget.rect().setHeight(occupiedHeight);
                    case Style.MARGIN_BOX -> widget.rect().setMarginHeight(occupiedHeight);
                }
            } else {
                switch (boxSizingY){
                    case Style.CONTENT_BOX -> widget.rect().setContentHeight(maxHeight);
                    case Style.BORDER_BOX -> widget.rect().setHeight(maxHeight);
                    case Style.MARGIN_BOX -> widget.rect().setMarginHeight(maxHeight);
                }
            }
        }
    }
    private void computeFlexAuto(IFlexWidget widget){
        int occupiedHeight = 0;
        int occupiedWidth = 0;
        int totalGrow = 0;
        int itemSpacingX = 0;
        int itemSpacingY = 0;

        FlexRect contentRect = widget.rect().getContentRect();
        String flexDirection = (String)widget.style(Style.FLEX_DIRECTION).getValue();

        IStyleSpec itemSpacingStyle = widget.style(Style.ITEM_SPACING);
        if (itemSpacingStyle != null){
            itemSpacingX = ((LengthPercentStyle.Data)itemSpacingStyle.getValue()).resolve(contentRect.getWidth());
            itemSpacingY = ((LengthPercentStyle.Data)itemSpacingStyle.getValue()).resolve(contentRect.getHeight());
        }


        for (IFlexWidget child : widget.children()) {
            if (!child.displayed()) continue;
            String positionStyle = (String)child.style(Style.POSITION).getValue();
            if (!positionStyle.equals(Style.FLEX)) continue;

            FlexRect rect = child.rect().getMarginRect();
            LengthPercentEnumStyle.Data childWidthData = (LengthPercentEnumStyle.Data) child.style(Style.WIDTH).getValue();
            LengthPercentEnumStyle.Data childHeightData = (LengthPercentEnumStyle.Data) child.style(Style.HEIGHT).getValue();

            if (!childWidthData.isString(Style.AUTO)) {
                occupiedWidth += rect.getWidth();
            }
            if (!childHeightData.isString(Style.AUTO)) {
                occupiedHeight += rect.getHeight();
            }

            IStyleSpec flexGrow = child.style(Style.FLEX_GROW);
            if (flexGrow != null){
                totalGrow += (int)flexGrow.getValue();
            }

            if (flexDirection.equals(Style.HORIZONTAL)){
                occupiedWidth += itemSpacingX;
            } else {
                occupiedHeight += itemSpacingY;
            }
        }


        for (IFlexWidget child : widget.children()) {
            if (!child.displayed()) continue;
            String positionStyle = (String)child.style(Style.POSITION).getValue();
            if (positionStyle.equals(Style.FLEX)) {

                IStyleSpec childFlexGrow = child.style(Style.FLEX_GROW);
                if (childFlexGrow == null) {
                    computeFlexAuto(child);
                    child.handleMeasuredEvent();
                    continue;
                }

                LengthPercentEnumStyle.Data childWidthData = (LengthPercentEnumStyle.Data) child.style(Style.WIDTH).getValue();
                LengthPercentEnumStyle.Data childHeightData = (LengthPercentEnumStyle.Data) child.style(Style.HEIGHT).getValue();
                String childBoxSizingX = (String) child.style(Style.BOX_SIZING_X).getValue();
                String childBoxSizingY = (String) child.style(Style.BOX_SIZING_Y).getValue();

                if (childWidthData.isString(Style.AUTO)) {
                    int widgetWidth = (contentRect.getWidth() - occupiedWidth) * ((int) childFlexGrow.getValue() / totalGrow);
                    switch (childBoxSizingX) {
                        case Style.CONTENT_BOX -> child.rect().setContentWidth(widgetWidth);
                        case Style.BORDER_BOX -> child.rect().setWidth(widgetWidth);
                        case Style.MARGIN_BOX -> child.rect().setMarginWidth(widgetWidth);
                    }
                }
                if (childHeightData.isString(Style.AUTO)) {
                    int widgetHeight = (contentRect.getHeight() - occupiedHeight) * ((int) childFlexGrow.getValue() / totalGrow);
                    switch (childBoxSizingY) {
                        case Style.CONTENT_BOX -> child.rect().setContentHeight(widgetHeight);
                        case Style.BORDER_BOX -> child.rect().setHeight(widgetHeight);
                        case Style.MARGIN_BOX -> child.rect().setMarginHeight(widgetHeight);
                    }
                }
            }
            computeFlexAuto(child);
            child.handleMeasuredEvent();
        }
    }
}
