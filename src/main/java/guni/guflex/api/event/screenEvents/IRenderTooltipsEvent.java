package guni.guflex.api.event.screenEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;
import net.minecraft.client.gui.GuiGraphics;

public interface IRenderTooltipsEvent extends IEventConsumableRegistrable1<IRenderTooltipsEvent.Data> {
    record Data(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int parentXOffset, int parentYOffset) {}
}
