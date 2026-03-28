package guni.guflex.common.drawable;

import guni.guflex.api.runtime.drawable.IDrawable;
import guni.guflex.api.utils.ResourceUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Size2i;

public class SpriteDrawable implements IDrawable {
    private final ResourceLocation spriteLocation;
    private final Size2i baseTextureSize;

    public SpriteDrawable(ResourceLocation spriteLocation, int textureWidth, int textureHeight){
        this.spriteLocation = spriteLocation;

        baseTextureSize = new Size2i(textureWidth, textureHeight);
    }
    public SpriteDrawable(ResourceLocation spriteLocation){
        this.spriteLocation = spriteLocation;
        this.baseTextureSize = ResourceUtils.getSpriteSizeFromLocation(spriteLocation);
    }

    @Override
    public Size2i getDefaultSize() {
        return baseTextureSize;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.blitSprite(spriteLocation, x, y, width, height);
    }
}
