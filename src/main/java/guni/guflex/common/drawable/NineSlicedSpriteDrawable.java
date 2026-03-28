package guni.guflex.common.drawable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import guni.guflex.api.runtime.drawable.IDrawable;
import guni.guflex.api.runtime.register.TextureAtlas;
import guni.guflex.api.utils.ResourceUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Size2i;
import org.joml.Matrix4f;

public class NineSlicedSpriteDrawable implements IDrawable {
    private final ResourceLocation spriteLocation;

    private final int sliceLeft;
    private final int sliceRight;
    private final int sliceTop;
    private final int sliceBottom;

    private final Size2i baseTextureSize;


    public NineSlicedSpriteDrawable(ResourceLocation spriteLocation,
                                    int textureWidth, int textureHeight,
                                    int sliceLeft, int sliceRight, int sliceTop, int sliceBottom){
        this.spriteLocation = spriteLocation;
        
        this.sliceLeft = sliceLeft;
        this.sliceRight = sliceRight;
        this.sliceTop = sliceTop;
        this.sliceBottom = sliceBottom;

        baseTextureSize = new Size2i(textureWidth, textureHeight);
    }
    
    public NineSlicedSpriteDrawable(ResourceLocation spriteLocation,
                                    int textureWidth, int textureHeight,
                                    int slice) {
        this.spriteLocation = spriteLocation;

        this.sliceLeft = slice;
        this.sliceRight = slice;
        this.sliceTop = slice;
        this.sliceBottom = slice;

        baseTextureSize = new Size2i(textureWidth, textureHeight);
    }

    public NineSlicedSpriteDrawable(ResourceLocation spriteLocation,
                                    int sliceLeft, int sliceRight, int sliceTop, int sliceBottom){
        this.spriteLocation = spriteLocation;

        this.sliceLeft = sliceLeft;
        this.sliceRight = sliceRight;
        this.sliceTop = sliceTop;
        this.sliceBottom = sliceBottom;

        this.baseTextureSize = ResourceUtils.getSpriteSizeFromLocation(spriteLocation);
    }

    public NineSlicedSpriteDrawable(ResourceLocation spriteLocation,
                                    int slice) {
        this.spriteLocation = spriteLocation;

        this.sliceLeft = slice;
        this.sliceRight = slice;
        this.sliceTop = slice;
        this.sliceBottom = slice;

        this.baseTextureSize = ResourceUtils.getSpriteSizeFromLocation(spriteLocation);
    }

    @Override
    public Size2i getDefaultSize() {
        return baseTextureSize;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(spriteLocation);

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();


        float uSize = maxU - minU;
        float VSize = maxV - minV;

        int xTopLeft = x;
        int yTopLeft = y;

        int xTopRight = width + x - sliceRight;
        int yTopRight = y;

        int xBottomLeft = x;
        int yBottomLeft = height + y - sliceBottom;

        int xBottomRight = width + x - sliceRight;
        int yBottomRight = height + y - sliceBottom;

        float maxLeftU = minU + (uSize * ((float)sliceLeft / (float)baseTextureSize.width));
        float maxTopV = minV + (VSize * ((float)sliceTop / (float)baseTextureSize.height));
        float minRightU = maxU - (uSize * ((float)sliceRight / (float)baseTextureSize.width));
        float minBottomV = maxV - (VSize * ((float)sliceBottom / (float)baseTextureSize.height));


        float maxLineU = maxU - (uSize * ((float)sliceRight / (float)baseTextureSize.width));
        float maxLineV = maxV - (VSize * ((float)sliceBottom / (float)baseTextureSize.height));
        float minLineU = minU + (uSize * ((float)sliceLeft / (float)baseTextureSize.width));
        float minLineV = minV + (VSize * ((float)sliceTop / (float)baseTextureSize.height));


        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, sprite.atlasLocation());

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        Matrix4f matrix = guiGraphics.pose().last().pose();

        //corners
        draw(bufferBuilder, matrix, minU, minV, maxLeftU, maxTopV, xTopLeft, yTopLeft, sliceLeft, sliceTop); //top left
        draw(bufferBuilder, matrix, minRightU, minV, maxU, maxTopV, xTopRight, yTopRight, sliceRight, sliceTop); //top Right
        draw(bufferBuilder, matrix, minU, minBottomV, maxLeftU, maxV, xBottomLeft, yBottomLeft, sliceLeft, sliceBottom); //bottom left
        draw(bufferBuilder, matrix, minRightU, minBottomV, maxU, maxV, xBottomRight, yBottomRight, sliceRight, sliceBottom); //bottom right

        //edges
        draw(bufferBuilder, matrix, minLineU, minV, maxLineU, maxTopV, xTopLeft + sliceLeft, yTopLeft, width - sliceLeft - sliceRight, sliceTop); //top
        draw(bufferBuilder, matrix, minLineU, minBottomV, maxLineU, maxV, xBottomLeft + sliceLeft, yBottomLeft, width - sliceLeft - sliceRight, sliceBottom); //Bottom
        draw(bufferBuilder, matrix, minU, minLineV, maxLeftU, maxLineV, xTopLeft, yTopLeft + sliceTop, sliceLeft, height - sliceTop - sliceBottom); //left
        draw(bufferBuilder, matrix, minRightU, minLineV, maxU, maxLineV, xTopRight, yTopRight + sliceTop, sliceRight, height - sliceTop - sliceBottom); //right

        //center
        draw(bufferBuilder, matrix, minLineU, minLineV, maxLineU, maxLineV, xTopLeft + sliceLeft, yTopLeft + sliceTop, width - sliceRight - sliceLeft, height - sliceTop - sliceBottom); //right

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }
    
    private static void draw(
            BufferBuilder bufferBuilder,
            Matrix4f matrix,
            float minU, double minV,
            float maxU, float maxV,
            int xOffset, int yOffset,
            int width, int height)
    {
        bufferBuilder.addVertex(matrix, (float)xOffset, (float)(yOffset + height), 0.0F).setUv(minU, maxV);
        bufferBuilder.addVertex(matrix, (float)(xOffset + width), (float)(yOffset + height), 0.0F).setUv(maxU, maxV);
        bufferBuilder.addVertex(matrix, (float)(xOffset + width), (float)yOffset, 0.0F).setUv(maxU, (float)minV);
        bufferBuilder.addVertex(matrix, (float)xOffset, (float)yOffset, 0.0F).setUv(minU, (float)minV);
    }
}
