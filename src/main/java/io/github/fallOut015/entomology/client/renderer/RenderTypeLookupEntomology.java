package io.github.fallOut015.entomology.client.renderer;

import io.github.fallOut015.entomology.block.BlocksEntomology;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RenderTypeLookupEntomology {
    public static void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlocksEntomology.BUG_JAR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlocksEntomology.TERRARIUM.get(), RenderType.cutout());
    }
}