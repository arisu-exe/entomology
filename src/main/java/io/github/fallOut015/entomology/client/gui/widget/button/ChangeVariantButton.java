package io.github.fallOut015.entomology.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.fallOut015.entomology.client.gui.screen.inventory.EntomologyGuideScreen;
import io.github.fallOut015.entomology.common.capabilities.CapabilitiesEntomology;
import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChangeVariantButton extends Button {
    final EntomologyGuideScreen screen;
    final int variant;
    boolean depressed;

    public ChangeVariantButton(int x, int y, final int variant, final EntomologyGuideScreen screen) {
        super(x, y, 20, 20, StringTextComponent.EMPTY, button -> screen.setVariant(variant));
        this.screen = screen;
        this.variant = variant;
    }

    public void renderButton(MatrixStack stack, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bind(EntomologyGuideScreen.ENTOMOLOGY_GUIDE_LOCATION);

        int i = this.isDepressed() ? 66 : 45;
        int j = !this.isDepressed() && this.isHovered() ? 215 : 194;

        this.blit(stack, this.x, this.y, i, j, 20, 20);

        int k = this.screen.getUForVariant(this.variant);
        int l = this.screen.getInsectV();
        int cutoff = this.isDepressed() ? 8 : 0;

        this.screen.getPlayer().getCapability(CapabilitiesEntomology.INSECT_TRACKER).ifPresent(insectTracker -> {
            if(!insectTracker.hasVariant((EntityType<? extends InsectEntity>) this.screen.getInsectEntityType(), this.variant)) {
                RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
            }
        });

        this.blit(stack, this.x + 2, this.y + 2 - cutoff / 4, k, l + cutoff, 16, 16 - cutoff);
    }

    public void playDownSound(SoundHandler soundHandler) {
        soundHandler.play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public boolean isDepressed() {
        return this.depressed;
    }
    public void unpress() {
        this.depressed = false;
    }
    public void depress() {
        this.depressed = true;
    }

    @Override
    public void onClick(double p_230982_1_, double p_230982_3_) {
        super.onClick(p_230982_1_, p_230982_3_);
        this.screen.unpress();
        this.depressed = true;
    }
}