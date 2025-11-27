package guni.guflex.common.drawable;

import guni.guflex.api.runtime.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.Size2i;

public class ItemDrawable implements IDrawable {
    private final int ITEM_WIDTH = 16;
    private final int ITEM_HEIGHT = 16;
    private final Size2i baseTextureSize;

    private final ItemStack item;

    public ItemDrawable(ItemStack item){
        this.item = item;

        baseTextureSize = new Size2i(ITEM_WIDTH, ITEM_HEIGHT);
    }

    public ItemDrawable(ResourceLocation itemLocation){
        this.item = new ItemStack(BuiltInRegistries.ITEM.get(itemLocation));

        baseTextureSize = new Size2i(ITEM_WIDTH, ITEM_HEIGHT);
    }

    @Override
    public Size2i getDefaultSize() {
        return baseTextureSize;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.renderItem(item, x, y);
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, item, x, y);
    }
}
