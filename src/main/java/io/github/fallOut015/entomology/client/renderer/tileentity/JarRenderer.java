package io.github.fallOut015.entomology.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.fallOut015.entomology.tileentity.BugJarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class JarRenderer extends TileEntityRenderer<BugJarTileEntity> {
    public JarRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BugJarTileEntity jar, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {
        if(jar.hasInsect()) {
            stack.pushPose();
            stack.scale(0.5f, 0.5f, 0.5f);
            stack.translate(1f, 0.5f, 1f);
            Minecraft.getInstance().getEntityRenderDispatcher().render(jar.getInsect(), 0d, 0d, 0d, 0f, p_225616_2_, stack, buffer, p_225616_5_);
            stack.popPose();
        }
    }
}