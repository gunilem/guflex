package guni.guflex.api.event.screenEvents;

import net.minecraft.client.gui.GuiGraphics;

public interface IRenderDebugEvent {
    record Data(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int parentXOffset, int parentYOffset, DebugInfo info) {}
    boolean onEvent(Data data);

    class DebugInfo{
        public enum DebugTrigger{
            Hover,
            AllUnder,
            Always,
            Never
        }

        public DebugTrigger debugTrigger = DebugTrigger.Never;
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
