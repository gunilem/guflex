package guni.guflex.api.event.screenEvents;

import guni.guflex.api.event.register.IEventRegistrable1;
import net.minecraft.client.gui.GuiGraphics;

public interface IRenderForegroundEvent extends IEventRegistrable1<IRenderForegroundEvent.Data> {
    record Data(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int parentXOffset, int parentYOffset) {}
}
