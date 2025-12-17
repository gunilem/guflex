package guni.guflex.api.style;

import guni.guflex.api.event.FlexWidgetEventHandler;
import guni.guflex.api.runtime.screen.IFlexScreen;
import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.common.style.LengthPercentEnumStyle;
import guni.guflex.common.style.LengthPercentStyle;
import guni.guflex.common.style.computer.PositionComputer;
import guni.guflex.common.style.computer.SizeComputer;
import guni.guflex.common.style.parser.EnumStyleParser;
import guni.guflex.common.style.parser.LengthPercentEnumParser;
import guni.guflex.common.style.parser.LengthPercentParser;
import net.minecraft.client.Minecraft;

public class FlexStyle {

    public FlexStyle(FlexWidgetEventHandler widgetEventHandler){
        widgetEventHandler.registerWidgetUpdatedEvent(this::cleanDirty);
    }

    protected int X = 0;
    public FlexStyle setX(int value){ X = value; setDirty(); return this; }
    public int getX(){ return X; }

    protected int Y = 0;
    public FlexStyle setY(int value){ Y = value; setDirty(); return this; }
    public int getY(){ return Y; }


    protected LengthPercentEnumStyle WIDTH = new LengthPercentEnumStyle(LengthPercentEnumStyle.Type.Percent, 1f);
    public FlexStyle setWidth(LengthPercentEnumStyle value){ WIDTH = value; setDirty(); return this; }
    public FlexStyle setWidth(String value){ WIDTH = LengthPercentEnumParser.parse(value, Style.WRAP, Style.AUTO); setDirty(); return this; }
    public LengthPercentEnumStyle getWidth(){ return WIDTH; }

    protected LengthPercentEnumStyle HEIGHT = new LengthPercentEnumStyle(LengthPercentEnumStyle.Type.Percent, 1f);
    public FlexStyle setHeight(LengthPercentEnumStyle value){ HEIGHT = value; setDirty(); return this; }
    public FlexStyle setHeight(String value){ HEIGHT = LengthPercentEnumParser.parse(value, Style.WRAP, Style.AUTO); setDirty(); return this; }
    public LengthPercentEnumStyle getHeight(){ return HEIGHT; }


    protected LengthPercentStyle LEFT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setLeft(LengthPercentStyle value){ LEFT = value; setDirty(); return this; }
    public FlexStyle setLeft(String value){ LEFT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getLeft(){ return LEFT; }

    protected LengthPercentStyle RIGHT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setRight(LengthPercentStyle value){ RIGHT = value; setDirty(); return this; }
    public FlexStyle setRight(String value){ RIGHT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getRight(){ return RIGHT; }

    protected LengthPercentStyle TOP = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setTop(LengthPercentStyle value){ TOP = value; setDirty(); return this; }
    public FlexStyle setTop(String value){ TOP = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getTop(){ return TOP; }

    protected LengthPercentStyle BOTTOM = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setBottom(LengthPercentStyle value){ BOTTOM = value; setDirty(); return this; }
    public FlexStyle setBottom(String value){ BOTTOM = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getBottom(){ return BOTTOM; }


    protected LengthPercentStyle MIN_WIDTH = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMinWidth(LengthPercentStyle value){ MIN_WIDTH = value; setDirty(); return this; }
    public FlexStyle setMinWidth(String value){ MIN_WIDTH = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMinWidth(){ return MIN_WIDTH; }

    protected LengthPercentStyle MIN_HEIGHT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMinHeight(LengthPercentStyle value){ MIN_HEIGHT = value; setDirty(); return this; }
    public FlexStyle setMinHeight(String value){ MIN_HEIGHT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMinHeight(){ return MIN_HEIGHT; }


    protected LengthPercentStyle MAX_WIDTH = new LengthPercentStyle(Integer.MAX_VALUE, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMaxWidth(LengthPercentStyle value){ MAX_WIDTH = value; setDirty(); return this; }
    public FlexStyle setMaxWidth(String value){ MAX_WIDTH = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMaxWidth(){ return MAX_WIDTH; }

    protected LengthPercentStyle MAX_HEIGHT = new LengthPercentStyle(Integer.MAX_VALUE, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMaxHeight(LengthPercentStyle value){ MAX_HEIGHT = value; setDirty(); return this; }
    public FlexStyle setMaxHeight(String value){ MAX_HEIGHT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMaxHeight(){ return MAX_HEIGHT; }


    protected LengthPercentStyle PADDING_LEFT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setPaddingLeft(LengthPercentStyle value){ PADDING_LEFT = value; setDirty(); return this; }
    public FlexStyle setPaddingLeft(String value){ PADDING_LEFT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getPaddingLeft(){ return PADDING_LEFT; }

    protected LengthPercentStyle PADDING_RIGHT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setPaddingRight(LengthPercentStyle value){ PADDING_RIGHT = value; setDirty(); return this; }
    public FlexStyle setPaddingRight(String value){ PADDING_RIGHT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getPaddingRight(){ return PADDING_RIGHT; }

    protected LengthPercentStyle PADDING_TOP = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setPaddingTop(LengthPercentStyle value){ PADDING_TOP = value; setDirty(); return this; }
    public FlexStyle setPaddingTop(String value){ PADDING_TOP = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getPaddingTop(){ return PADDING_TOP; }

    protected LengthPercentStyle PADDING_BOTTOM = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setPaddingBottom(LengthPercentStyle value){ PADDING_BOTTOM = value; setDirty(); return this; }
    public FlexStyle setPaddingBottom(String value){ PADDING_BOTTOM = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getPaddingBottom(){ return PADDING_BOTTOM; }


    protected LengthPercentStyle MARGIN_LEFT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMarginLeft(LengthPercentStyle value){ MARGIN_LEFT = value; setDirty(); return this; }
    public FlexStyle setMarginLeft(String value){ MARGIN_LEFT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMarginLeft(){ return MARGIN_LEFT; }

    protected LengthPercentStyle MARGIN_RIGHT = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMarginRight(LengthPercentStyle value){ MARGIN_RIGHT = value; setDirty(); return this; }
    public FlexStyle setMarginRight(String value){ MARGIN_RIGHT = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMarginRight(){ return MARGIN_RIGHT; }

    protected LengthPercentStyle MARGIN_TOP = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMarginTop(LengthPercentStyle value){ MARGIN_TOP = value; setDirty(); return this; }
    public FlexStyle setMarginTop(String value){ MARGIN_TOP = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMarginTop(){ return MARGIN_TOP; }

    protected LengthPercentStyle MARGIN_BOTTOM = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setMarginBottom(LengthPercentStyle value){ MARGIN_BOTTOM = value; setDirty(); return this; }
    public FlexStyle setMarginBottom(String value){ MARGIN_BOTTOM = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getMarginBottom(){ return MARGIN_BOTTOM; }


    protected LengthPercentStyle ITEM_SPACING = new LengthPercentStyle(0, LengthPercentStyle.Type.Pixel);
    public FlexStyle setItemSpacing(LengthPercentStyle value){ ITEM_SPACING = value; setDirty(); return this; }
    public FlexStyle setItemSpacing(String value){ ITEM_SPACING = LengthPercentParser.parse(value); setDirty(); return this; }
    public LengthPercentStyle getItemSpacing(){ return ITEM_SPACING; }


    protected String BOX_SIZING_X = Style.CONTENT_BOX;
    public FlexStyle setBoxSizingX(String value){ BOX_SIZING_X = EnumStyleParser.parse(value, Style.CONTENT_BOX, Style.BORDER_BOX, Style.MARGIN_BOX); setDirty(); return this; }
    public String getBoxSizingX(){ return BOX_SIZING_X; }

    protected String BOX_SIZING_Y = Style.CONTENT_BOX;
    public FlexStyle setBoxSizingY(String value){ BOX_SIZING_Y = EnumStyleParser.parse(value, Style.CONTENT_BOX, Style.BORDER_BOX, Style.MARGIN_BOX); setDirty(); return this; }
    public String getBoxSizingY(){ return BOX_SIZING_Y; }

    protected String FLEX_DIRECTION = Style.NONE;
    public FlexStyle setFlexDirection(String value){ FLEX_DIRECTION = EnumStyleParser.parse(value, Style.NONE, Style.VERTICAL, Style.HORIZONTAL); setDirty(); return this; }
    public String getFlexDirection(){ return FLEX_DIRECTION; }

    protected String FLEX_LAYOUT = Style.NONE;
    public FlexStyle setFlexLayout(String value){ FLEX_LAYOUT = EnumStyleParser.parse(value, Style.NONE, Style.GRID); setDirty(); return this; }
    public String getFlexLayout(){ return FLEX_LAYOUT; }

    protected String POSITION = Style.FLEX;
    public FlexStyle setPosition(String value){ POSITION = EnumStyleParser.parse(value, Style.RELATIVE, Style.FIXED, Style.FLEX); setDirty(); return this; }
    public String getPosition(){ return POSITION; }


    protected int LAYOUT_PRIORITY = 0;
    public FlexStyle setLayoutPriority(int value){ LAYOUT_PRIORITY = value; setDirty(); return this; }
    public int getLayoutPriority(){ return LAYOUT_PRIORITY; }

    protected int FLEX_GROW = 0;
    public FlexStyle setFlexGrow(int value){ FLEX_GROW = value; setDirty(); return this; }
    public int getFlexGrow(){ return FLEX_GROW; }


    protected String ANCHOR = Style.TOP_LEFT;
    public FlexStyle setAnchor(String value){ ANCHOR = EnumStyleParser.parse(value,
            Style.TOP_LEFT, Style.TOP_RIGHT,
            Style.BOTTOM_LEFT, Style.BOTTOM_RIGHT,
            Style.LEFT_CENTER, Style.RIGHT_CENTER,
            Style.TOP_CENTER, Style.BOTTOM_CENTER,
            Style.CENTER); setDirty();
        return this; }
    public String getAnchor(){ return ANCHOR; }

    protected String PIVOT = Style.TOP_LEFT;
    public FlexStyle setPivot(String value){ PIVOT = EnumStyleParser.parse(value,
            Style.TOP_LEFT, Style.TOP_RIGHT,
            Style.BOTTOM_LEFT, Style.BOTTOM_RIGHT,
            Style.LEFT_CENTER, Style.RIGHT_CENTER,
            Style.TOP_CENTER, Style.BOTTOM_CENTER,
            Style.CENTER); setDirty();
        return this; }
    public String getPivot(){ return PIVOT; }



    protected boolean isDirty;
    protected void cleanDirty() { isDirty = false; }
    public boolean isDirty() { return isDirty; }
    public void setDirty() {
        if (Minecraft.getInstance().screen instanceof IFlexScreen screen) screen.onHierarchyUpdated();
        isDirty = true;

        dependsOnChildren = WIDTH.isKeyword(Style.WRAP) || HEIGHT.isKeyword(Style.WRAP) || !FLEX_DIRECTION.equals(Style.NONE);
    }

    private boolean dependsOnChildren = true;
    public boolean dependsOnChildren() { return dependsOnChildren; }

    public void measure(IFlexWidget parent, IFlexWidget widget){
        if (parent == null){
            SizeComputer.measure(FlexRect.EMPTY(), widget);
            return;
        }
        FlexRect parentContentRect = parent.rect().getContentRect();
        SizeComputer.measure(parentContentRect, widget);
    }
    public void position(IFlexWidget parent, IFlexWidget widget){
        if (parent == null){
            PositionComputer.position(FlexRect.EMPTY() , widget);
            cleanDirty();
            return;
        }
        FlexRect parentContentRect = parent.rect().getContentRect();
        PositionComputer.position(parentContentRect, widget);
        cleanDirty();
    }
}
