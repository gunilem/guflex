package guni.guflex.core.registers;

import guni.guflex.api.runtime.register.TextureAtlas;
import net.minecraft.client.Minecraft;

public class Internals {
    private static Textures textures;

    public static Textures getTextures(){
        if (textures == null){
            TextureAtlas uploader = new TextureAtlas(Minecraft.getInstance().getTextureManager(), Constants.GUFLEX_ICON_ATLAS_LOCATION, Constants.GUFLEX_ICON_ATLAS_LOCATION);
            textures = new Textures(uploader);
        }

        return textures;
    }
}
