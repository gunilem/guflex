package guni.guflex.api.runtime.register;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TextureAtlas extends TextureAtlasHolder {
    protected ResourceLocation textureAtlasLocation;

    public TextureAtlas(TextureManager textureManager, ResourceLocation textureAtlasLocation, ResourceLocation atlasInfoLocation) {
        super(textureManager, textureAtlasLocation, atlasInfoLocation);

        this.textureAtlasLocation = textureAtlasLocation;
    }

    public @NotNull TextureAtlasSprite getSprite(@NotNull ResourceLocation location) {
        return super.getSprite(location);
    }

    public ResourceLocation getTextureAtlasLocation(){
        return textureAtlasLocation;
    }
}