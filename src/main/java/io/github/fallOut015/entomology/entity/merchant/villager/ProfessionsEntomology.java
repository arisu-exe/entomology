package io.github.fallOut015.entomology.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;
import io.github.fallOut015.entomology.MainEntomology;
import io.github.fallOut015.entomology.util.SoundEventsEntomology;
import io.github.fallOut015.entomology.village.POITypesEntomology;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ProfessionsEntomology {
    private static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, MainEntomology.MODID);



    public static final RegistryObject<VillagerProfession> ENTOMOLOGIST = PROFESSIONS.register("entomologist", () -> new VillagerProfession("entomologist", POITypesEntomology.ENTOMOLOGIST.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEventsEntomology.VILLAGER_WORK_ENTOMOLOGIST));



    public static void register(IEventBus bus) {
        PROFESSIONS.register(bus);
    }
}