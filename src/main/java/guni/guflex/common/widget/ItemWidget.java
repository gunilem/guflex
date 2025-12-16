package guni.guflex.common.widget;

import guni.guflex.api.event.screenEvents.IRenderEvent;
import guni.guflex.api.runtime.widget.FlexWidget;
import guni.guflex.api.style.Style;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemWidget extends FlexWidget {
    private final ItemStack item;

    public ItemWidget(ItemStack item){
        super();
        this.item = item;

        getStyle().setWidth("16px").setHeight("16px");

        eventHandler.registerRenderEvent(this::onRender);
    }

    public ItemWidget(ResourceLocation itemLocation){
        this(new ItemStack(BuiltInRegistries.ITEM.get(itemLocation)));
    }

    protected void onRender(IRenderEvent.Data event){
        event.guiGraphics().renderItem(item, bounds.getX(), bounds.getY());
        event.guiGraphics().renderItemDecorations(Minecraft.getInstance().font, item, bounds.getX(), bounds.getY());
    }
}
