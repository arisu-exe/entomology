package io.github.fallOut015.entomology.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.fallOut015.entomology.client.renderer.entity.model.ButterflyModel;
import io.github.fallOut015.entomology.entity.insect.ButterflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer extends MobRenderer<ButterflyEntity, ButterflyModel<ButterflyEntity>> {
    /*public static final ResourceLocation[] TEXTURE = {
        new ResourceLocation("entomology", "textures/entity/insect/butterfly/butterfly_black_yellow.png"),
        new ResourceLocation("entomology", "textures/entity/insect/butterfly/butterfly_blue_black.png"),
        new ResourceLocation("entomology", "textures/entity/insect/butterfly/butterfly_red_black.png"),
        new ResourceLocation("entomology", "textures/entity/insect/butterfly/butterfly_white_black.png")
    };*/
    // TODO array again?
    public ButterflyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ButterflyModel<>(), 0);
    }

    @Override
    protected void scale(ButterflyEntity entity, MatrixStack stack, float p_225620_3_) {
        stack.scale(entity.getSize(), entity.getSize(), entity.getSize());
    }
    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity entity) {
        return new ResourceLocation("entomology", "textures/entity/insect/butterfly/butterfly_" + ButterflyEntity.VARIANTS[entity.getVariant()] + ".png");
    }
}