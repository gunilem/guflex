package guni.guflex.api.runtime.drawable;

import guni.guflex.api.style.FlexRect;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.common.util.Size2i;

public interface IDrawable {
    void draw(GuiGraphics guiGraphics, int x, int y, int width, int height);
    default void draw(GuiGraphics guiGraphics, FlexRect rect){
        draw(guiGraphics, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    Size2i getDefaultSize();
}
