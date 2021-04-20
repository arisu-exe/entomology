package io.github.fallOut015.entomology.village;

import io.github.fallOut015.entomology.MainEntomology;
import io.github.fallOut015.entomology.block.BlocksEntomology;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class POITypesEntomology {
    private static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, MainEntomology.MODID);



    public static final RegistryObject<PointOfInterestType> ENTOMOLOGIST = POI_TYPES.register("entomologist", () -> new PointOfInterestType("entomologist", PointOfInterestType.getBlockStates(BlocksEntomology.TERRARIUM.get()), 1, 1));



    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
    }
}
