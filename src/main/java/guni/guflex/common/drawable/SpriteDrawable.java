package guni.guflex.common.drawable;

import guni.guflex.api.runtime.drawable.IDrawable;
import guni.guflex.api.runtime.register.TextureAtlas;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Size2i;

public class SpriteDrawable implements IDrawable {
    private final TextureAtlas textureAtlas;
    private final ResourceLocation spriteLocation;
    private final Size2i baseTextureSize;

    public SpriteDrawable(TextureAtlas textureAtlas, ResourceLocation spriteLocation, int textureWidth, int textureHeight){
        this.textureAtlas = textureAtlas;
        this.spriteLocation = spriteLocation;

        baseTextureSize = new Size2i(textureWidth, textureHeight);
    }

    @Override
    public Size2i getDefaultSize() {
        return baseTextureSize;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        TextureAtlasSprite sprite = textureAtlas.getSprite(spriteLocation);
        guiGraphics.blit(x, y, 0, width, height, sprite);
    }
}
