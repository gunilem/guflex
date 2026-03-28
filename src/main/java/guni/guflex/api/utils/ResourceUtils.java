package guni.guflex.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Size2i;

public class ResourceUtils {
    public static Size2i getSpriteSizeFromLocation(ResourceLocation spriteLocation){
        TextureAtlasSprite resource = Minecraft.getInstance().getGuiSprites().getSprite(spriteLocation);
        try (SpriteContents contents = resource.contents()) {
            return new Size2i(contents.width(), contents.height());
        }
    }
}
