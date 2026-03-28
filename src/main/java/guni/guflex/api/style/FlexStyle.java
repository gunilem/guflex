package guni.guflex.api.style;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        widgetEventHandler.widget.updated.register(this::cleanDirty);
    }
    
    public FlexStyle copyProperties(FlexStylePreset preset){
        if (preset == null) return this;
        if (preset.X != null) X = preset.X;
        if (preset.Y != null) Y = preset.Y;

        if (preset.WIDTH != null) WIDTH = preset.WIDTH;
        if (preset.HEIGHT != null) HEIGHT = preset.HEIGHT;

        if (preset.LEFT != null) LEFT = preset.LEFT;
        if (preset.RIGHT != null) RIGHT = preset.RIGHT;
        if (preset.TOP != null) TOP = preset.TOP;
        if (preset.BOTTOM != null) BOTTOM = preset.BOTTOM;

        if (preset.MIN_WIDTH != null) MIN_WIDTH = preset.MIN_WIDTH;
        if (preset.MIN_HEIGHT != null) MIN_HEIGHT = preset.MIN_HEIGHT;

        if (preset.MAX_WIDTH != null) MAX_WIDTH = preset.MAX_WIDTH;
        if (preset.MAX_HEIGHT != null) MAX_HEIGHT = preset.MAX_HEIGHT;

        if (preset.PADDING_LEFT != null) PADDING_LEFT = preset.PADDING_LEFT;
        if (preset.PADDING_RIGHT != null) PADDING_RIGHT = preset.PADDING_RIGHT;
        if (preset.PADDING_TOP != null) PADDING_TOP = preset.PADDING_TOP;
        if (preset.PADDING_BOTTOM != null) PADDING_BOTTOM = preset.PADDING_BOTTOM;

        if (preset.MARGIN_LEFT != null) MARGIN_LEFT = preset.MARGIN_LEFT;
        if (preset.MARGIN_RIGHT != null) MARGIN_RIGHT = preset.MARGIN_RIGHT;
        if (preset.MARGIN_TOP != null) MARGIN_TOP = preset.MARGIN_TOP;
        if (preset.MARGIN_BOTTOM != null) MARGIN_BOTTOM = preset.MARGIN_BOTTOM;

        if (preset.ITEM_SPACING != null) ITEM_SPACING = preset.ITEM_SPACING;

        if (preset.BOX_SIZING_X != null) BOX_SIZING_X = preset.BOX_SIZING_X;
        if (preset.BOX_SIZING_Y != null) BOX_SIZING_Y = preset.BOX_SIZING_Y;

        if (preset.FLEX_DIRECTION != null) FLEX_DIRECTION = preset.FLEX_DIRECTION;
        if (preset.FLEX_LAYOUT != null) FLEX_LAYOUT = preset.FLEX_LAYOUT;

        if (preset.POSITION != null) POSITION = preset.POSITION;

        if (preset.LAYOUT_PRIORITY != null) LAYOUT_PRIORITY = preset.LAYOUT_PRIORITY;

        if (preset.FLEX_GROW != null) FLEX_GROW = preset.FLEX_GROW;

        if (preset.ANCHOR != null) ANCHOR = preset.ANCHOR;
        if (preset.PIVOT != null) PIVOT = preset.PIVOT;

        setDirty();

        return this;
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
    
    public static class FlexStylePreset {
        public Integer X = null;
        public Integer Y = null;
        
        public LengthPercentEnumStyle WIDTH = null;
        public LengthPercentEnumStyle HEIGHT = null;

        public LengthPercentStyle LEFT = null;
        public LengthPercentStyle RIGHT = null;
        public LengthPercentStyle TOP = null;
        public LengthPercentStyle BOTTOM = null;

        public LengthPercentStyle MIN_WIDTH = null;
        public LengthPercentStyle MIN_HEIGHT = null;
        
        public LengthPercentStyle MAX_WIDTH = null;
        public LengthPercentStyle MAX_HEIGHT = null;

        public LengthPercentStyle PADDING_LEFT = null;
        public LengthPercentStyle PADDING_RIGHT = null;
        public LengthPercentStyle PADDING_TOP = null;
        public LengthPercentStyle PADDING_BOTTOM = null;
        
        public LengthPercentStyle MARGIN_LEFT = null;
        public LengthPercentStyle MARGIN_RIGHT = null;
        public LengthPercentStyle MARGIN_TOP = null;
        public LengthPercentStyle MARGIN_BOTTOM = null;
        
        public LengthPercentStyle ITEM_SPACING = null;
        
        public String BOX_SIZING_X = null;
        public String BOX_SIZING_Y = null;
        public String FLEX_DIRECTION = null;
        public String FLEX_LAYOUT = null;
        public String POSITION = null;

        public Integer LAYOUT_PRIORITY = null;
        public Integer FLEX_GROW = null;

        public String ANCHOR = null;
        public String PIVOT = null;


        public void setX(int value){ X = value; }
        public void setY(int value){ Y = value; }

        public void setWidth(String value){ WIDTH = LengthPercentEnumParser.parse(value, Style.WRAP, Style.AUTO); }
        public void setHeight(String value){ HEIGHT = LengthPercentEnumParser.parse(value, Style.WRAP, Style.AUTO); }

        public void setLeft(String value){ LEFT = LengthPercentParser.parse(value); }
        public void setRight(String value){ RIGHT = LengthPercentParser.parse(value); }
        public void setTop(String value){ TOP = LengthPercentParser.parse(value); }
        public void setBottom(String value){ BOTTOM = LengthPercentParser.parse(value); }

        public void setMinWidth(String value){ MIN_WIDTH = LengthPercentParser.parse(value); }
        public void setMinHeight(String value){ MIN_HEIGHT = LengthPercentParser.parse(value); }

        public void setMaxWidth(String value){ MAX_WIDTH = LengthPercentParser.parse(value); }
        public void setMaxHeight(String value){ MAX_HEIGHT = LengthPercentParser.parse(value); }

        public void setPaddingLeft(String value){ PADDING_LEFT = LengthPercentParser.parse(value); }
        public void setPaddingRight(String value){ PADDING_RIGHT = LengthPercentParser.parse(value); }
        public void setPaddingTop(String value){ PADDING_TOP = LengthPercentParser.parse(value); }
        public void setPaddingBottom(String value){ PADDING_BOTTOM = LengthPercentParser.parse(value); }

        public void setMarginLeft(String value){ MARGIN_LEFT = LengthPercentParser.parse(value); }
        public void setMarginRight(String value){ MARGIN_RIGHT = LengthPercentParser.parse(value); }
        public void setMarginTop(String value){ MARGIN_TOP = LengthPercentParser.parse(value); }
        public void setMarginBottom(String value){ MARGIN_BOTTOM = LengthPercentParser.parse(value); }

        public void setItemSpacing(String value){ ITEM_SPACING = LengthPercentParser.parse(value); }

        public void setBoxSizingX(String value){ BOX_SIZING_X = EnumStyleParser.parse(value, Style.CONTENT_BOX, Style.BORDER_BOX, Style.MARGIN_BOX); }
        public void setBoxSizingY(String value){ BOX_SIZING_Y = EnumStyleParser.parse(value, Style.CONTENT_BOX, Style.BORDER_BOX, Style.MARGIN_BOX); }

        public void setFlexDirection(String value){ FLEX_DIRECTION = EnumStyleParser.parse(value, Style.NONE, Style.VERTICAL, Style.HORIZONTAL); }
        public void setFlexLayout(String value){ FLEX_LAYOUT = EnumStyleParser.parse(value, Style.NONE, Style.GRID); }
        public void setPosition(String value){ POSITION = EnumStyleParser.parse(value, Style.RELATIVE, Style.FIXED, Style.FLEX); }

        public void setLayoutPriority(int value){ LAYOUT_PRIORITY = value; }

        public void setFlexGrow(int value){ FLEX_GROW = value; }

        public void setAnchor(String value){ ANCHOR = EnumStyleParser.parse(value,
                Style.TOP_LEFT, Style.TOP_RIGHT,
                Style.BOTTOM_LEFT, Style.BOTTOM_RIGHT,
                Style.LEFT_CENTER, Style.RIGHT_CENTER,
                Style.TOP_CENTER, Style.BOTTOM_CENTER,
                Style.CENTER); }
        public void setPivot(String value){ PIVOT = EnumStyleParser.parse(value,
                Style.TOP_LEFT, Style.TOP_RIGHT,
                Style.BOTTOM_LEFT, Style.BOTTOM_RIGHT,
                Style.LEFT_CENTER, Style.RIGHT_CENTER,
                Style.TOP_CENTER, Style.BOTTOM_CENTER,
                Style.CENTER); }
        
        public FlexStylePreset(JsonObject json){
            if (json == null) return;

            json.entrySet()
                    .forEach(entry -> setProperty(entry.getKey(), entry.getValue().getAsString()));
        }

        private void setProperty(String property, String value) {
            switch (property) {
                case Style.X -> setX(Integer.parseInt(value));
                case Style.Y -> setY(Integer.parseInt(value));

                case Style.WIDTH -> setWidth(value);
                case Style.HEIGHT -> setHeight(value);

                case Style.LEFT -> setLeft(value);
                case Style.RIGHT -> setRight(value);
                case Style.TOP -> setTop(value);
                case Style.BOTTOM -> setBottom(value);

                case Style.MIN_WIDTH -> setMinWidth(value);
                case Style.MIN_HEIGHT -> setMinHeight(value);

                case Style.MAX_WIDTH -> setMaxWidth(value);
                case Style.MAX_HEIGHT -> setMaxHeight(value);

                case Style.PADDING_LEFT -> setPaddingLeft(value);
                case Style.PADDING_RIGHT -> setPaddingRight(value);
                case Style.PADDING_TOP -> setPaddingTop(value);
                case Style.PADDING_BOTTOM -> setPaddingBottom(value);

                case Style.MARGIN_LEFT -> setMarginLeft(value);
                case Style.MARGIN_RIGHT -> setMarginRight(value);
                case Style.MARGIN_TOP -> setMarginTop(value);
                case Style.MARGIN_BOTTOM -> setMarginBottom(value);

                case Style.ITEM_SPACING -> setItemSpacing(value);

                case Style.BOX_SIZING_X -> setBoxSizingX(value);
                case Style.BOX_SIZING_Y -> setBoxSizingY(value);

                case Style.FLEX_DIRECTION -> setFlexDirection(value);
                case Style.FLEX_LAYOUT -> setFlexLayout(value);

                case Style.POSITION -> setPosition(value);

                case Style.LAYOUT_PRIORITY -> setLayoutPriority(Integer.parseInt(value));

                case Style.FLEX_GROW -> setFlexGrow(Integer.parseInt(value));

                case Style.ANCHOR -> setAnchor(value);
                case Style.PIVOT -> setPivot(value);


                default -> throw new IllegalArgumentException("Unknown property: " + property);
            }
        }
    }
}
