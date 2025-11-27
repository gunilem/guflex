package guni.guflex.common.widget;

import guni.guflex.api.event.screenEvents.IRenderEvent;
import guni.guflex.api.event.screenEvents.IScreenTickEvent;
import guni.guflex.api.runtime.screen.IFlexScreen;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemLoopWidget extends FlexWidget {
    private final List<ItemStack> items;
    private int currentIndex;

    private final int swapTimer;

    public ItemLoopWidget(List<ItemStack> items, int swapTimer){
        super();
        this.items = items;
        this.swapTimer = swapTimer;

        addStyle(Style.WIDTH, "16px");
        addStyle(Style.HEIGHT, "16px");

        eventHandler.registerRenderEvent(this::onRender);
        eventHandler.registerScreenTickEvent(this::onTick);
    }

    public ItemLoopWidget(List<ItemStack> items){
        this(items, 20);
    }

    protected void onRender(IRenderEvent.Data event){
        ItemStack item = items.get(currentIndex);
        event.guiGraphics().renderItem(item, bounds.getX(), bounds.getY());
        event.guiGraphics().renderItemDecorations(Minecraft.getInstance().font, item, bounds.getX(), bounds.getY());
    }

    private void onTick(IScreenTickEvent.Data event){
        currentIndex = (event.tickSinceScreenOpened() / swapTimer) % items.size();
    }
}
