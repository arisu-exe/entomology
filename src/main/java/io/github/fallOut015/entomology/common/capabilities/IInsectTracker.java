package io.github.fallOut015.entomology.common.capabilities;

import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import net.minecraft.entity.EntityType;

public interface IInsectTracker {
    String getCollectedInsects();
    void setCollectedInsects(String insects);

    void addInsect(EntityType<? extends InsectEntity> insect);
    void addVariant(EntityType<? extends InsectEntity> insect, int variant);
    boolean hasInsect(EntityType<? extends InsectEntity> insect);
    boolean hasVariant(EntityType<? extends InsectEntity> insect, int variant);
}