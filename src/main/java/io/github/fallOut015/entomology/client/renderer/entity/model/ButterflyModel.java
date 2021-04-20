package io.github.fallOut015.entomology.client.renderer.entity.model;

// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.entomology.entity.insect.ButterflyEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyModel<T extends ButterflyEntity> extends EntityModel<T> {
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;

    public ButterflyModel() {
        this.texWidth = 16;
        this.texHeight = 16;

        this.leftWing = new ModelRenderer(this);
        this.leftWing.setPos(0.0F, 24.0F, 0.0F);
        setRotationAngle(this.leftWing, 0.0F, 0.0F, -0.3927F);
        this.leftWing.texOffs(-6, 0).addBox(0.0F, 0.0F, -3.0F, 8.0F, 0.0F, 6.0F, 0.0F, true);

        this.rightWing = new ModelRenderer(this);
        this.rightWing.setPos(0.0F, 24.0F, 0.0F);
        setRotationAngle(this.rightWing, 0.0F, 0.0F, 0.3927F);
        this.rightWing.texOffs(-6, 0).addBox(-8.0F, 0.0F, -3.0F, 8.0F, 0.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        leftWing.zRot = MathHelper.cos(limbSwing) * -2f * limbSwingAmount - 0.3927F;
        rightWing.zRot = MathHelper.cos(limbSwing) * 2f * limbSwingAmount + 0.3927F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
        rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}