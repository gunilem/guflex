package guni.guflex.api.style;

public class FlexRect {
    protected int x, y;
    protected int width, height;
    protected int minWidth, minHeight;
    protected int maxWidth, maxHeight;
    protected int paddingLeft, paddingRight, paddingTop, paddingBottom;
    protected int marginLeft, marginRight, marginTop, marginBottom;
    protected int left, right, top, bottom;
    protected String pivot;

    public static FlexRect EMPTY(){
        return new FlexRect(0, 0, 0, 0);
    }

    public FlexRect(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setMarginWidth(int width) {
        this.width = Math.clamp(width + marginLeft + marginRight - left - right, minWidth, maxWidth);
    }
    public void setWidth(int width) {
        this.width = Math.clamp(width - left - right, minWidth, maxWidth);
    }
    public void setContentWidth(int width) {
        this.width = Math.clamp(width + paddingLeft + paddingRight - left - right, minWidth, maxWidth);
    }

    public void setMarginHeight(int height) {
        this.height = Math.clamp(height - marginBottom - marginTop - top - bottom, minHeight, maxHeight);
    }
    public void setHeight(int height) {
        this.height = Math.clamp(height - top - bottom, minHeight, maxHeight);
    }
    public void setContentHeight(int height) {
        this.height = Math.clamp(height + paddingBottom + paddingTop - top - bottom, minHeight, maxHeight);
    }

    public void addToX(int x) {
        this.x += x;
    }
    public void addToY(int y) {
        this.y += y;
    }
    public void addToWidth(int width) {
        this.width = Math.clamp(this.width + width, minWidth, maxWidth);
    }
    public void addToHeight(int height) {
        this.height = Math.clamp(this.height + height, minHeight, maxHeight);
    }

    public void setPadding(int paddingLeft, int paddingRight, int paddingTop, int paddingBottom){
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
    }
    public void setMargin(int marginLeft, int marginRight, int marginTop, int marginBottom){
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
    }
    public void setCropping(int left, int right, int top, int bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }
    public void setPivot(String pivot){
        this.pivot = pivot;
    }
    public void setMinSize(Integer minWidth, Integer minHeight){
        this.minWidth = minWidth == null ? 0 : minWidth;
        this.minHeight = minHeight == null ? 0 : minHeight;
    }
    public void setMaxSize(Integer maxWidth, Integer maxHeight){
        this.maxWidth = maxWidth == null ? Integer.MAX_VALUE : maxWidth;
        this.maxHeight = maxHeight == null ? Integer.MAX_VALUE : maxHeight;
    }

    public FlexRect getContentRect(){
        return new FlexRect(getX() + paddingLeft, getY() + paddingTop, getWidth() - paddingLeft - paddingRight, getHeight() - paddingTop - paddingBottom);
    }
    public FlexRect getMarginRect(){
        return new FlexRect(getX() - marginLeft, getY() - marginTop, getWidth() + marginLeft + marginRight, getHeight() + marginTop + marginBottom);
    }

    public boolean contains(double otherX, double otherY){
        return (otherX >= getX() && otherX <= getX() + width)
                && (otherY >= getY() && otherY <= getY() + height);
    }
    public boolean contains(int otherX, int otherY){
        return (otherX >= getX() && otherX <= getX() + width)
                && (otherY >= getY() && otherY <= getY() + height);
    }
    public boolean renderable(){
        return width - paddingLeft - paddingRight > 0 && height - paddingTop - paddingBottom > 0;
    }

    public void setX(int x){
        if (pivot == null) {
            this.x = x + left;
            return;
        }
        switch (pivot) {
            case ("top-left"), ("bottom-left"), ("left-center") -> this.x = x + marginLeft + left;
            case ("top-center"), ("bottom-center"), ("center") -> this.x = x - width / 2;
            case ("top-right"), ("bottom-right"), ("right-center") -> this.x = x - width - marginRight - right;
            default -> this.x = x;
        }
    }
    public int getX(){
        return x;
    }
    public void setY(int y){
        if (pivot == null){
            this.y = y + top;
            return;
        }
        switch (pivot) {
            case ("top-left"), ("top-right"), ("top-center") -> this.y = y + marginTop + top;
            case ("left-center"), ("right-center"), ("center") -> this.y = y - height / 2;
            case ("bottom-left"), ("bottom-right"), ("bottom-center") -> this.y = y - height - marginBottom - bottom;
            default -> this.y = y;
        }
    }
    public int getY(){
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public FlexRect transformRect(int width, int height, String anchor, String pivot){
        FlexRect rect = new FlexRect(0, 0, width, height);
        rect.pivot = pivot;
        rect.setX(x);
        rect.setY(y);
        switch (anchor) {
            case ("top-left") -> {}
            case ("top-center") -> { rect.addToX(this.width / 2); }
            case ("top-right") -> { rect.addToX(this.width); }

            case ("left-center") -> { rect.addToY(this.height / 2); }
            case ("center") -> { rect.addToX(this.width / 2); rect.addToY(this.height / 2); }
            case ("right-center") -> { rect.addToX(this.width); rect.addToY(this.height / 2); }

            case ("bottom-left") -> rect.addToY(this.height);
            case ("bottom-center") -> { rect.addToX(this.width / 2); rect.addToY(this.height); }
            case ("bottom-right") -> { rect.addToX(this.width); rect.addToY(this.height); }
        }

        return rect;
    }
    public FlexRect transformRect(int width, int height, String anchor, String pivot, int xOffset, int yOffset){
        FlexRect rect = new FlexRect(0, 0, width, height);
        rect.pivot = pivot;
        rect.setX(x);
        rect.setY(y);
        switch (anchor) {
            case ("top-left") -> {}
            case ("top-center") -> { rect.addToX(this.width / 2 + xOffset); }
            case ("top-right") -> { rect.addToX(this.width + xOffset); }

            case ("left-center") -> { rect.addToY(this.height / 2 + yOffset); }
            case ("center") -> { rect.addToX(this.width / 2 + xOffset); rect.addToY(this.height / 2 + yOffset); }
            case ("right-center") -> { rect.addToX(this.width + xOffset); rect.addToY(this.height / 2 + yOffset); }

            case ("bottom-left") -> rect.addToY(this.height + yOffset);
            case ("bottom-center") -> { rect.addToX(this.width / 2 + xOffset); rect.addToY(this.height + yOffset); }
            case ("bottom-right") -> { rect.addToX(this.width + xOffset); rect.addToY(this.height + yOffset); }
        }

        rect.addToX(xOffset);
        rect.addToY(yOffset);
        return rect;
    }
}
