package io.github.fallOut015.entomology.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.entomology.client.renderer.entity.model.FireflyModel;
import io.github.fallOut015.entomology.entity.insect.FireflyEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireflyRenderer extends MobRenderer<FireflyEntity, FireflyModel<FireflyEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("entomology", "textures/entity/insect/firefly/firefly.png");

    public FireflyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FireflyModel<>(), 0);
    }

    @Override
    public void render(FireflyEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225623_6_) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.eyes(this.getTextureLocation(entity)));
        double pulse = 0.25d * (Math.sin(entity.tickCount * 0.25d) + 1d);
        stack.pushPose();
        stack.translate(0, -1.25d, 0);
        this.model.renderToBuffer(stack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F - (float) pulse, 1.0F - (float) pulse, 1.0F - (float) pulse, 1.0F);
        stack.popPose();
    }

    // TODO also, probably a half opacity glow around 2x2 model

    @Override
    protected void scale(FireflyEntity entity, MatrixStack stack, float p_225620_3_) {
        stack.scale(entity.getSize(), entity.getSize(), entity.getSize());
    }
    @Override
    public ResourceLocation getTextureLocation(FireflyEntity entity) {
        return TEXTURE;
    }
}