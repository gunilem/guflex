package guni.guflex.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen {

    @Redirect(
            method = "renderSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"
            )
    )
    private void redirectTranslate(PoseStack instance, float x, float y, float z) {
        // Change the z value or skip the translation
        instance.translate(x, y, 0.0F); // Example: Force z=0
    }
}
