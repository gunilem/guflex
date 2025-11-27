package guni.guflex.api.event.screenEvents;

import net.minecraft.client.gui.GuiGraphics;

public interface IRenderForegroundEvent {
    record Data(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int parentXOffset, int parentYOffset) {}
    void onEvent(Data data);
}
