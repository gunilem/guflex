package guni.guflex.core.registers;

import guni.guflex.api.runtime.register.TextureAtlas;
import guni.guflex.common.drawable.NineSlicedSpriteDrawable;
import guni.guflex.common.drawable.SpriteDrawable;
import net.minecraft.resources.ResourceLocation;

public class Textures {
    private final TextureAtlas textureAtlas;

    public final NineSlicedSpriteDrawable button_background;
    public final NineSlicedSpriteDrawable button_background_off;
    public final NineSlicedSpriteDrawable text_field_background;

    public final SpriteDrawable menu_icon;
    public final SpriteDrawable close_icon;
    public final SpriteDrawable toggle_box_empty;
    public final SpriteDrawable toggle_box_full;

    public Textures(TextureAtlas uploader) {
        this.textureAtlas = uploader;

        this.button_background = new NineSlicedSpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "button_background"), 32, 32, 4);
        this.button_background_off = new NineSlicedSpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "button_background_off"), 32, 32, 4);
        this.text_field_background = new NineSlicedSpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "text_field_background"), 18, 18,1, 1, 1, 1);


        this.menu_icon = new SpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "menu_icon"), 32, 32);
        this.close_icon = new SpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "close_icon"), 36, 36);
        this.toggle_box_empty = new SpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "toggle_box_empty"), 36, 36);
        this.toggle_box_full = new SpriteDrawable(textureAtlas, ResourceLocation.fromNamespaceAndPath("guflex", "toggle_box_full"), 36, 36);
    }

    public TextureAtlas getAtlas(){
        return textureAtlas;
    }
}
