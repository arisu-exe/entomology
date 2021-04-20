package io.github.fallOut015.entomology.client.renderer.entity.model;

// Made with Blockbench 3.8.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.entomology.entity.insect.FireflyEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FireflyModel<T extends FireflyEntity> extends EntityModel<T> {
	private final ModelRenderer firefly;

	public FireflyModel() {
		this.texWidth = 8;
		this.texHeight = 8;

		this.firefly = new ModelRenderer(this);
		this.firefly.setPos(0.0F, 24.0F, 0.0F);
		this.firefly.texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.firefly.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}