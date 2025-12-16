package guni.guflex.common.widget;

import guni.guflex.api.event.inputEvents.*;
import guni.guflex.api.event.register.one.EventRegister;
import guni.guflex.api.event.screenEvents.IRenderEvent;
import guni.guflex.api.event.screenEvents.IScreenTickEvent;
import guni.guflex.api.runtime.screen.IFlexScreen;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.Style;
import guni.guflex.core.registers.Internals;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TextField extends FlexWidget {
    public final EventRegister<String> onValueChanged;
    public final EventRegister<String> onValueSubmitted;

    public static class Position{
        public int x;
        public int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    protected boolean editable;
    protected int maxLineLength;
    protected boolean multiline;
    protected Component templateValue;

    private boolean focused;
    private Position selectionBegin;
    private Position selectionEnd;
    private Position caretPosition;


    protected List<String> lines;
    private Font font;

    protected int yOffset = 0;
    protected int xOffset = 0;
    protected int scrollYSensibility = 10;
    protected int scrollXSensibility = 10;

    public TextField(
            boolean editable,
            int maxLineLength,
            boolean multiline,
            String defaultValue,
            String templateValue
    ) {
        super();
        this.onValueChanged = new EventRegister<>();
        this.onValueSubmitted = new EventRegister<>();
        this.editable = editable;
        this.maxLineLength = maxLineLength;
        this.multiline = multiline;
        this.templateValue = Component.literal(templateValue).withStyle(net.minecraft.network.chat.Style.EMPTY.withColor(0x333333).withItalic(true));
        this.lines = new ArrayList<>();

        this.font = Minecraft.getInstance().font;

        selectionBegin = new Position(0, 0);
        selectionEnd = new Position(0, 0);
        caretPosition = new Position(0, 0);

        setValue(defaultValue);

        getStyle()
                .setWidth("100%").setHeight("20")
                .setPaddingLeft("5px").setPaddingRight("5px").setPaddingTop("5px").setPaddingBottom("5px")
                .setBoxSizingX(Style.BORDER_BOX).setBoxSizingY(Style.BORDER_BOX);

        setBackground(Internals.getTextures().text_field_background);

        eventHandler.registerRenderEvent(this::onRender);

        eventHandler.registerMouseClickedUnconsumedEvent(this::onMouseClickedUnconsumed);
        eventHandler.registerMouseReleasedUnconsumedEvent(this::onMouseReleasedUnconsumed);
        eventHandler.registerMouseScrolledUnconsumedEvent(this::onMouseScrolledUnconsumed);
        eventHandler.registerMouseDraggedUnconsumedEvent(this::onMouseDraggedUnconsumed);
        eventHandler.registerKeyPressedUnconsumedEvent(this::onKeyPressedUnconsumed);
        eventHandler.registerCharTypedUnconsumedEvent(this::onCharTypedUnconsumed);

        eventHandler.registerMouseClickedConsumedEvent(this::onMouseClickedConsumed);
        eventHandler.registerMouseReleasedConsumedEvent(this::onMouseReleasedConsumed);

        eventHandler.registerScreenTickEvent(this::onScreenTick);
    }

    public boolean isEmpty(){
        for (String line : lines){
            if (!line.isEmpty()) return false;
        }
        return true;
    }
    public String value(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++){
            sb.append(lines.get(i));
            if (i < lines.size() - 1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    public void setValue(String value){
        if (!editable) return;
        lines.clear();
        lines = transformStringToArray(value);
        onValueChanged.invoke(value());
        
    }
    public boolean canConsumeInput(){
        return editable && focused;
    }
    public void insertText(String textToInsert) {
        List<String> values = transformStringToArray(textToInsert);

        eraseSelection();

        if (values.size() > 1){
            String lastValuesElement = values.getLast();

            String firstLine = getLineBeforeSelection() + values.getFirst();
            String LastLine = lastValuesElement + getLineAfterSelection();

            if (!values.isEmpty()) {
                values.removeFirst();
                values.removeLast();
            }

            lines.set(caretPosition.y, firstLine);
            lines.addAll(caretPosition.y + 1, values);
            lines.add(caretPosition.y + values.size() + 1, LastLine);

            caretPosition.y = caretPosition.y + values.size() + 1;
            caretPosition.x = lastValuesElement.length();
            scrollToCaret(caretPosition.x, caretPosition.y);
        }
        else {
            String before = getLineBeforeSelection();
            String after = getLineAfterSelection();

            String merged = before + values.getFirst() + after;
            lines.set(caretPosition.y, merged);
            caretPosition.x += values.getFirst().length();
            scrollToCaret(caretPosition.x, caretPosition.y);
        }

    }
    protected void scrollToCaret(int x, int y){
        FlexRect contentRect = rect().getContentRect();
        if (font.width(lines.get(y).substring(0, x)) - xOffset >= contentRect.getWidth()){
            xOffset = font.width(lines.get(y).substring(0, x) + "_") - contentRect.getWidth();
        }
        if (font.width(lines.get(y).substring(0, x)) - xOffset <= 0){
            xOffset = font.width(lines.get(y).substring(0, x));
        }
        if (y * font.lineHeight - yOffset > contentRect.getHeight()){
            yOffset = y * font.lineHeight - contentRect.getHeight();
        }
        if (y * font.lineHeight - yOffset < 0){
            yOffset = y * font.lineHeight;
        }
        clampScroll();
    }
    protected List<String> transformStringToArray(String text){
        List<String> lines = new ArrayList<>();
        if (text == null){
            lines.add("");
            return lines;
        }

        if (!multiline){
            List<String> tempLines = text.lines().toList();
            if (tempLines.isEmpty()){
                lines.add("");
            }
            else {
                lines.add(tempLines.getFirst());
            }
        }
        else {
            lines.addAll(text.lines().toList());
        }

        if (lines.isEmpty()){
            lines.add("");
        }
        return lines;
    }
    protected void deleteChar(){
        if (isSelected()){
            eraseSelection();
        }
        else {
            if (caretPosition.x <= 0){
                if (caretPosition.y <= 0) return;
                String line = lines.get(caretPosition.y);
                lines.remove(caretPosition.y);
                caretPosition.y--;
                String mergedLine = lines.get(caretPosition.y) + line;
                lines.set(caretPosition.y, mergedLine);
                caretPosition.x = mergedLine.length() - line.length();
            }
            else {
                String lineBefore = lines.get(caretPosition.y).substring(0, caretPosition.x - 1);
                String lineAfter = lines.get(caretPosition.y).substring(caretPosition.x);
                String mergedLine = lineBefore + lineAfter;
                lines.set(caretPosition.y, mergedLine);
                caretPosition.x--;
            }
            scrollToCaret(caretPosition.x, caretPosition.y);
            
        }
    }
    protected void supprChar(){
        if (isSelected()){
            eraseSelection();
        }
        else {
            if (caretPosition.x >= lines.get(caretPosition.y).length()){
                if (caretPosition.y >= lines.size() - 1) return;
                String line = lines.get(caretPosition.y + 1);
                lines.remove(caretPosition.y + 1);
                String mergedLine = lines.get(caretPosition.y) + line;
                lines.set(caretPosition.y, mergedLine);
                caretPosition.x = mergedLine.length();
                scrollToCaret(caretPosition.x, caretPosition.y);
            }
            else {
                String lineBefore = lines.get(caretPosition.y).substring(0, caretPosition.x);
                String lineAfter = lines.get(caretPosition.y).substring(caretPosition.x + 1);
                String mergedLine = lineBefore + lineAfter;
                lines.set(caretPosition.y, mergedLine);
            }
            
        }
    }
    protected void eraseSelection(){
        if (!isSelected()) return;

        String mergedLine = getLineBeforeSelection() + getLineAfterSelection();

        if (selectionEnd.y > selectionBegin.y + 1) {
            lines.subList(selectionBegin.y + 1, selectionEnd.y).clear();
        }

        lines.set(selectionBegin.y, mergedLine);
        if (selectionEnd.y != selectionBegin.y){
            lines.remove(selectionBegin.y + 1);
        }

        selectionEnd.y = selectionBegin.y;
        selectionEnd.x = selectionBegin.x;
        
    }
    protected String getLineBeforeSelection(){
        if (isSelected()){
            return lines.get(selectionBegin.y).substring(0, selectionBegin.x);
        }
        else {
            return lines.get(caretPosition.y).substring(0, caretPosition.x);
        }
    }
    protected String getLineAfterSelection(){
        if (isSelected()) {
            return lines.get(selectionEnd.y).substring(selectionEnd.x);
        }
        else {
            return lines.get(caretPosition.y).substring(caretPosition.x);
        }
    }
    public void clear(){
        lines.clear();
        lines.add("");
        caretPosition.x = 0;
        caretPosition.y = 0;
        selectionBegin.x = 0;
        selectionBegin.y = 0;
        selectionEnd.x = 0;
        selectionEnd.y = 0;
        
    }

    private Position beginDragPosition;

    protected int mouseX = 0;
    protected int mouseY = 0;
    protected boolean dragged = false;

    protected void onRender(IRenderEvent.Data event) {
        FlexRect contentRect = rect().getContentRect();
        event.guiGraphics().enableScissor(
                contentRect.getX() + event.parentXOffset(),
                contentRect.getY() + event.parentYOffset(),
                contentRect.getX() + contentRect.getWidth() + event.parentXOffset(),
                contentRect.getY() + contentRect.getHeight() + event.parentYOffset()
        );

        event.guiGraphics().pose().translate(-this.xOffset, -this.yOffset, 0);

        renderText(event.guiGraphics(), event.partialTick(), mouseX, mouseY);

        event.guiGraphics().pose().translate(this.xOffset, this.yOffset, 0);

        event.guiGraphics().disableScissor();
    }
    protected void renderText(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY){
        FlexRect contentRect = rect().getContentRect();

        if (!focused && isEmpty()){
            guiGraphics.drawString(font, templateValue, contentRect.getX(), contentRect.getY(), 0, false);
            return;
        }

        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(font, lines.get(i), contentRect.getX(), contentRect.getY() + font.lineHeight * i, 0, false);
        }
        int xPosition = contentRect.getX() + font.width(lines.get(caretPosition.y).substring(0, caretPosition.x));
        int yPosition = contentRect.getY() + caretPosition.y * font.lineHeight;

        boolean renderCaret = (Util.getMillis() / 300L) % 2L == 0;
        boolean renderSelection = isSelected();

        if (!focused) return;
        if (renderSelection){
            int xBeginPosition = contentRect.getX() + font.width(lines.get(selectionBegin.y).substring(0, selectionBegin.x));
            int xEndPosition = contentRect.getX() + font.width(lines.get(selectionEnd.y).substring(0, selectionEnd.x));

            int yBeginPosition = contentRect.getY() + selectionBegin.y * font.lineHeight;
            int yEndPosition = contentRect.getY() + selectionEnd.y * font.lineHeight;

            if (selectionBegin.y == selectionEnd.y){
                guiGraphics.fill(
                        xBeginPosition,
                        yBeginPosition,
                        xEndPosition,
                        yBeginPosition + font.lineHeight, 0x77777777);
                return;
            }

            for (int i = 0; i < selectionEnd.y - selectionBegin.y + 1; i++){
                if (i == 0){
                    guiGraphics.fill(
                            xBeginPosition,
                            yBeginPosition,
                            contentRect.getX() + font.width(lines.get(selectionBegin.y)),
                            yBeginPosition + font.lineHeight, 0x77777777);
                }
                else if (i == selectionEnd.y - selectionBegin.y){
                    guiGraphics.fill(
                            contentRect.getX(),
                            yEndPosition,
                            xEndPosition,
                            yEndPosition + font.lineHeight, 0x77777777);
                }
                else {
                    guiGraphics.fill(
                            contentRect.getX(),
                            yBeginPosition + i * font.lineHeight,
                            contentRect.getX() + font.width(lines.get(selectionBegin.y + i)),
                            yBeginPosition + (i + 1) * font.lineHeight, 0x77777777);
                }
            }
        }
        else if (renderCaret)
        {
            if (caretPosition.x >= lines.get(caretPosition.y).length()){
                guiGraphics.drawString(font, "_", xPosition, yPosition, 0);
            } else {
                guiGraphics.fill(xPosition, yPosition, xPosition + 1, yPosition + font.lineHeight, 0xFF000000);
            }
        }
    }

    protected boolean onMouseClickedUnconsumed(IMouseClickedEvent.Data event) {
        if (!editable) return false;
        if (!rect().contains(event.mouseX(), event.mouseY())) {
            focused = false;
            return false;
        }

        Position position = getPositionFromCursorPosition((int)event.mouseX(), (int)event.mouseY());
        if (position == null) return false;
        beginDragPosition = position;

        caretPosition.x = position.x;
        caretPosition.y = position.y;

        focused = true;


        selectionBegin.x = position.x;
        selectionBegin.y = position.y;
        selectionEnd.x = position.x;
        selectionEnd.y = position.y;

        return true;
    }
    protected boolean onMouseReleasedUnconsumed(IMouseReleasedEvent.Data event) {
        dragged = false;
        return false;
    }
    protected boolean onMouseScrolledUnconsumed(IMouseScrolledEvent.Data event) {
        if (!focused) return false;
        if (!rect().contains(event.mouseX(), event.mouseY())) return false;

        if (event.scrollX() != 0) xOffset += event.scrollX() > 0 ? -scrollXSensibility : scrollXSensibility;
        if (event.scrollY() != 0) yOffset += event.scrollY() > 0 ? -scrollYSensibility : scrollYSensibility;
        clampScroll();

        return true;
    }
    protected boolean onMouseDraggedUnconsumed(IMouseDraggedEvent.Data event) {
        if (!editable) return false;
        if (!focused) return false;
        dragged = true;
        mouseX = (int) event.mouseX();
        mouseY = (int) event.mouseY();

        return true;
    }
    protected boolean onKeyPressedUnconsumed(IKeyPressedEvent.Data event) {
        if (focused && editable) {
            switch (event.keyCode()) {
                case 257: //enter
                case 335: //keypad enter
                    if (multiline){
                        addLineBreak();
                        return true;
                    }
                    else if (onValueSubmitted == null) {
                        return false;
                    }
                    onValueSubmitted.invoke(value());
                    return true;
                case 259: //delete
                    this.deleteChar();
                    onValueChanged.invoke(value());

                    return true;
                case 260:
                case 266:
                case 267:
                default:

                    if (Screen.isSelectAll(event.keyCode())) {
                        selectionBegin.x = 0;
                        selectionBegin.y = 0;
                        selectionEnd.x = lines.getLast().length();
                        selectionEnd.y = lines.size() - 1;
                        caretPosition.x = selectionBegin.x;
                        caretPosition.y = selectionBegin.y;
                        return true;
                    } else if (Screen.isCopy(event.keyCode())) {
                        Minecraft.getInstance().keyboardHandler.setClipboard(this.getSelection());
                        return true;
                    } else if (Screen.isPaste(event.keyCode())) {
                        this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
                        onValueChanged.invoke(value());

                        return true;
                    } else {
                        if (Screen.isCut(event.keyCode())) {
                            Minecraft.getInstance().keyboardHandler.setClipboard(this.getSelection());
                            this.eraseSelection();
                            onValueChanged.invoke(value());


                            return true;
                        }

                        return true;
                    }
                case 261://suppr
                    this.supprChar();
                    onValueChanged.invoke(value());

                    return true;
                case 262://right arrow
                    if (caretPosition.x + 1 <= lines.get(caretPosition.y).length()){
                        caretPosition.x++;
                    }
                    scrollToCaret(caretPosition.x, caretPosition.y);
                    return true;
                case 263://left arrow
                    if (caretPosition.x > 0){
                        caretPosition.x--;
                    }
                    scrollToCaret(caretPosition.x, caretPosition.y);
                    return true;
                case 264://down arrow
                    if (caretPosition.y + 1 < lines.size()){
                        caretPosition.y++;
                        if (caretPosition.x >= lines.get(caretPosition.y).length()){
                            caretPosition.x = lines.get(caretPosition.y).length() - 1;
                        }
                    }
                    scrollToCaret(caretPosition.x, caretPosition.y);
                    return true;
                case 265://up arrow
                    if (caretPosition.y > 0){
                        caretPosition.y--;
                        if (caretPosition.x >= lines.get(caretPosition.y).length()){
                            caretPosition.x = lines.get(caretPosition.y).length() - 1;
                        }
                    }
                    scrollToCaret(caretPosition.x, caretPosition.y);
                    return true;
            }
        } else {
            return false;
        }
    }
    protected boolean onCharTypedUnconsumed(ICharTypedEvent.Data event) {
        if (!canConsumeInput()) return false;
        if (!StringUtil.isAllowedChatCharacter(event.codePoint())) return false;

        insertText(Character.toString(event.codePoint()));
        onValueChanged.invoke(value());

        return true;
    }

    protected void onMouseClickedConsumed(IMouseClickedEvent.Data event) {
        focused = false;
    }
    protected void onMouseReleasedConsumed(IMouseReleasedEvent.Data event) {
        dragged = false;
    }




    protected void onScreenTick(IScreenTickEvent.Data event) {
        if (!editable) return;
        if (!focused) return;
        if (!dragged) return;

        Position position = getPositionFromCursorPosition(mouseX, mouseY);
        if (position == null) return;

        if (position.y == beginDragPosition.y){
            selectionBegin.y = beginDragPosition.y;
            selectionEnd.y = beginDragPosition.y;
            if (position.x < beginDragPosition.x){
                selectionBegin.x = position.x;
                selectionEnd.x = beginDragPosition.x;
            }
            else {
                selectionBegin.x = beginDragPosition.x;
                selectionEnd.x = position.x;
            }
        }
        else {
            if (position.y < beginDragPosition.y){
                selectionBegin.x = position.x;
                selectionBegin.y = position.y;
                selectionEnd.x = beginDragPosition.x;
                selectionEnd.y = beginDragPosition.y;
            }
            else {
                selectionBegin.x = beginDragPosition.x;
                selectionBegin.y = beginDragPosition.y;
                selectionEnd.x = position.x;
                selectionEnd.y = position.y;
            }
        }
        FlexRect contentRect = rect().getContentRect();

        if (mouseX < contentRect.getX()){
            xOffset -= contentRect.getX() - mouseX;
            clampScroll();
        }
        else if (mouseX > contentRect.getX() + contentRect.getWidth()){
            xOffset += mouseX - (contentRect.getX() + contentRect.getWidth());
            clampScroll();
        }
        if (mouseY < contentRect.getY()){
            yOffset -= contentRect.getY() - mouseY;
            clampScroll();
        }
        else if (mouseY > contentRect.getY() + contentRect.getHeight()){
            yOffset += mouseY - (contentRect.getY() + contentRect.getHeight());
            clampScroll();
        }
        caretPosition.x = selectionBegin.x;
        caretPosition.y = selectionBegin.y;
    }

    protected Position getPositionFromCursorPosition(int x, int y){
        FlexRect contentRect = rect().getContentRect();
        int xInBounds = (x + xOffset - (contentRect.getX()));
        int yInBounds = (y + yOffset - (contentRect.getY()));

        int internalY = yInBounds / font.lineHeight;
        if (internalY >= lines.size()) { internalY = lines.size() - 1; }
        if (internalY < 0) return null;


        String before = font.plainSubstrByWidth(lines.get(internalY), xInBounds);
        String after;
        if (before.length() < lines.get(internalY).length()){
            after = before + lines.get(internalY).charAt(before.length());
        }
        else {
            after = before;
        }

        int internalX;
        if (Math.abs(font.width(before) - xInBounds) < Math.abs(font.width(after) - xInBounds)){
            internalX = before.length();
        }
        else {
            internalX = after.length();
        }

        return new Position(internalX, internalY);
    }
    protected boolean isSelected(){
        return selectionBegin.x != selectionEnd.x || selectionBegin.y != selectionEnd.y;
    }
    protected void addLineBreak(){
        eraseSelection();

        String lineBefore = getLineBeforeSelection();
        String lineAfter = getLineAfterSelection();

        lines.set(caretPosition.y, lineBefore);
        lines.add(caretPosition.y + 1, lineAfter);

        caretPosition.y++;
        caretPosition.x = 0;
        scrollToCaret(caretPosition.x, caretPosition.y);
        
    }
    public String getSelection(){
        if (!isSelected()) return "";

        if (selectionBegin.y == selectionEnd.y){
            return lines.get(selectionBegin.y).substring(selectionBegin.x, selectionEnd.x);
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(lines.get(selectionBegin.y).substring(selectionBegin.x)).append("\n");
            for (int i = selectionBegin.y + 1; i < selectionEnd.y; i++){
                sb.append(i).append("\n");
            }
            sb.append(lines.get(selectionEnd.y), 0, selectionEnd.x);
            return sb.toString();
        }
    }
    protected void clampScroll(){
        FlexRect contentRect = rect().getContentRect();
        int maxWidth = 0;
        for (String line : lines){
            maxWidth = Math.max(maxWidth, font.width(line + "_"));
        }
        xOffset = Math.clamp(xOffset, 0, Math.max(0, maxWidth - contentRect.getWidth()));
        yOffset = Math.clamp(yOffset, 0, Math.max(0, font.lineHeight * lines.size() - contentRect.getHeight()));
    }
}
