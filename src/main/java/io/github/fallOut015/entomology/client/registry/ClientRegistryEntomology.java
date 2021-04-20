package io.github.fallOut015.entomology.client.registry;

import io.github.fallOut015.entomology.client.renderer.tileentity.JarRenderer;
import io.github.fallOut015.entomology.tileentity.TileEntitiesEntomology;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientRegistryEntomology {
    public static void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(TileEntitiesEntomology.BUG_JAR.get(), JarRenderer::new);
    }
}
