package guni.guflex.api.event.screenEvents;

import guni.guflex.api.event.register.IEventConsumableRegistrable1;
import net.minecraft.client.gui.GuiGraphics;

public interface IRenderDebugEvent extends IEventConsumableRegistrable1<IRenderDebugEvent.Data> {
    record Data(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int parentXOffset, int parentYOffset, DebugInfo info) {}

    class DebugInfo{
        public enum DebugTrigger{
            Hover,
            AllUnder,
            Always,
            Never
        }

        public DebugTrigger debugTrigger = DebugTrigger.Hover;
        public boolean marginDebug;
        public boolean borderDebug;
        public boolean paddingDebug;
        public boolean displayDebugBox;

        public void setDebugTrigger(DebugTrigger value){ debugTrigger = value; }
        public void setMarginDebug(boolean value) { marginDebug = value; }
        public void setBorderDebug(boolean value) { borderDebug = value; }
        public void setPaddingDebug(boolean value) { paddingDebug = value; }
        public void setDisplayDebugBox(boolean value) { displayDebugBox = value; }
    }
}
