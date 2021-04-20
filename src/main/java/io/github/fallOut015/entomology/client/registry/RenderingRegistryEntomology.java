package io.github.fallOut015.entomology.client.registry;

import io.github.fallOut015.entomology.client.renderer.entity.ButterflyRenderer;
import io.github.fallOut015.entomology.client.renderer.entity.FireflyRenderer;
import io.github.fallOut015.entomology.entity.EntitiesEntomology;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RenderingRegistryEntomology {
    public static void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntitiesEntomology.BUTTERFLY.get(), ButterflyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitiesEntomology.FIREFLY.get(), FireflyRenderer::new);
    }
}