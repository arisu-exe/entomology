package io.github.fallOut015.entomology;

import io.github.fallOut015.entomology.block.BlocksEntomology;
import io.github.fallOut015.entomology.client.registry.ClientRegistryEntomology;
import io.github.fallOut015.entomology.client.registry.RenderingRegistryEntomology;
import io.github.fallOut015.entomology.client.renderer.RenderTypeLookupEntomology;
import io.github.fallOut015.entomology.common.capabilities.CapabilitiesEntomology;
import io.github.fallOut015.entomology.entity.EntitiesEntomology;
import io.github.fallOut015.entomology.entity.ai.attributes.GlobalEntityTypeAttributesEntomology;
import io.github.fallOut015.entomology.entity.merchant.villager.ProfessionsEntomology;
import io.github.fallOut015.entomology.item.ItemModelPropertiesEntomology;
import io.github.fallOut015.entomology.item.ItemsEntomology;
import io.github.fallOut015.entomology.tileentity.TileEntitiesEntomology;
import io.github.fallOut015.entomology.village.POITypesEntomology;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MainEntomology.MODID)
public class MainEntomology  {
    public static final String MODID = "entomology";

    public MainEntomology() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        BlocksEntomology.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemsEntomology.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntitiesEntomology.register(FMLJavaModLoadingContext.get().getModEventBus());
        POITypesEntomology.register(FMLJavaModLoadingContext.get().getModEventBus());
        ProfessionsEntomology.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntitiesEntomology.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilitiesEntomology.setup(event);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistryEntomology.doClientStuff(event);
        ItemModelPropertiesEntomology.doClientStuff(event);
        RenderTypeLookupEntomology.doClientStuff(event);
        ClientRegistryEntomology.doClientStuff(event);
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
    }
    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEntityAttributeCreation(final EntityAttributeCreationEvent event) {
            GlobalEntityTypeAttributesEntomology.onEntityAttributeCreation(event);
        }
    }
    @Mod.EventBusSubscriber
    public static class Events {
    	@SubscribeEvent
    	public static void onBiomeLoad(final BiomeLoadingEvent event) {
            if(event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.JUNGLE || event.getCategory() == Biome.Category.PLAINS) {
                event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(EntitiesEntomology.BUTTERFLY.get(), 20, 1, 3));
            }
            if(event.getCategory() == Biome.Category.FOREST) {
                event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(EntitiesEntomology.FIREFLY.get(), 20, 2, 4));
            }
            // TODO put in own file
    	}
        @SubscribeEvent
        public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            CapabilitiesEntomology.onAttachCapabilities(event);
        }
    }
}