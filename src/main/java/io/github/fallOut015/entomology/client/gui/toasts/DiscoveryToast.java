package io.github.fallOut015.entomology.client.gui.toasts;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.fallOut015.entomology.client.gui.screen.inventory.EntomologyGuideScreen;
import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class DiscoveryToast implements IToast {
    final InsectEntity insect;
    private boolean playedSound;

    public DiscoveryToast(final InsectEntity insect) {
        this.insect = insect;
    }

    @Override
    public Visibility render(MatrixStack stack, ToastGui toastGui, long timer) {
        toastGui.getMinecraft().getTextureManager().bind(TEXTURE);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        toastGui.blit(stack, 0, 0, 0, 0, this.width(), this.height());
        //if (displayinfo != null) {
            List<IReorderingProcessor> list = toastGui.getMinecraft().font.split(this.insect.getFullName(), 125);
            int i = 16776960;
            if (list.size() == 1) {
                toastGui.getMinecraft().font.draw(stack, new TranslationTextComponent("discovery.toast.title"), 30.0F, 7.0F, i | -16777216);
                toastGui.getMinecraft().font.draw(stack, list.get(0), 30.0F, 18.0F, -1);
            } else {
                int j = 1500;
                float f = 300.0F;
                if (timer < 1500L) {
                    int k = MathHelper.floor(MathHelper.clamp((float)(1500L - timer) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                    toastGui.getMinecraft().font.draw(stack, new TranslationTextComponent("discovery.toast.title"), 30.0F, 11.0F, i | k);
                } else {
                    int i1 = MathHelper.floor(MathHelper.clamp((float)(timer - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                    int l = this.height() / 2 - list.size() * 9 / 2;

                    for(IReorderingProcessor ireorderingprocessor : list) {
                        toastGui.getMinecraft().font.draw(stack, ireorderingprocessor, 30.0F, (float)l, 16777215 | i1);
                        l += 9;
                    }
                }
            }

            if (!this.playedSound && timer > 0L) {
                this.playedSound = true;
            }

            toastGui.getMinecraft().getTextureManager().bind(EntomologyGuideScreen.ENTOMOLOGY_GUIDE_LOCATION);
            toastGui.blit(stack, 8, 8, this.insect.getU(), this.insect.getV(), 16, 16);

            return timer >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        /*} else {
            return IToast.Visibility.HIDE;
        }*/
    }
}
