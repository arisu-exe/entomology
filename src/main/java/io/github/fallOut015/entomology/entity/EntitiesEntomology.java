package io.github.fallOut015.entomology.entity;

import io.github.fallOut015.entomology.MainEntomology;
import io.github.fallOut015.entomology.entity.insect.ButterflyEntity;
import io.github.fallOut015.entomology.entity.insect.FireflyEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntitiesEntomology {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MainEntomology.MODID);



    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = ENTITIES.register("butterfly", () -> EntityType.Builder.of(ButterflyEntity::new, EntityClassification.AMBIENT).sized(0.5f, 0.5f).build("butterfly"));
    public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY = ENTITIES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, EntityClassification.AMBIENT).sized(0.25f, 0.25f).build("firefly"));
    // cicada
    // beetle
    // cricket
    // dragonfly
         // variants:
    // grasshopper
    // moth
        // variants:
    // wasp
    // mosquito
    // ant
    // caterpillar
        // variants:
    // ladybug
    // mantis
    // fly
        // variants:
    // centipede
    // roly-poly
    // cockroach
    // shield bug



    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
