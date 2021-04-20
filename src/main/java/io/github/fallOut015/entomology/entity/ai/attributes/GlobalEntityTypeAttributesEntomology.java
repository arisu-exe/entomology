package io.github.fallOut015.entomology.entity.ai.attributes;

import io.github.fallOut015.entomology.entity.EntitiesEntomology;
import io.github.fallOut015.entomology.entity.insect.ButterflyEntity;
import io.github.fallOut015.entomology.entity.insect.FireflyEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class GlobalEntityTypeAttributesEntomology {
    public static void onEntityAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(EntitiesEntomology.BUTTERFLY.get(), ButterflyEntity.applyAttributes().build());
        event.put(EntitiesEntomology.FIREFLY.get(), FireflyEntity.applyAttributes().build());
    }
}