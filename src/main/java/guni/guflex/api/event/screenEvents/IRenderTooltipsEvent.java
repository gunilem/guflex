package guni.guflex.api.event.screenEvents;

import net.minecraft.client.gui.GuiGraphics;

public interface IRenderTooltipsEvent {
    record Data(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int parentXOffset, int parentYOffset) {}
    boolean onEvent(Data data);
}
