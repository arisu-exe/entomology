package io.github.fallOut015.entomology.common.capabilities;

import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import net.minecraft.entity.EntityType;

public class InsectTracker implements IInsectTracker {
    String insects;

    public InsectTracker() {
        this.insects = "";
    }

    @Override
    public String getCollectedInsects() {
        return this.insects;
    }
    @Override
    public void setCollectedInsects(String insects) {
        this.insects = insects;
    }

    @Override
    public void addInsect(EntityType<? extends InsectEntity> insect) {
        if(!this.hasInsect(insect)) {
            this.insects = this.insects + insect.getRegistryName() + "{};";
        }
    }
    @Override
    public void addVariant(EntityType<? extends InsectEntity> insect, int variant) {
        if(this.hasInsect(insect) && !this.hasVariant(insect, variant)) {
            StringBuilder target = new StringBuilder();
            for(int i = this.insects.indexOf(insect.getRegistryName().toString()) + insect.getRegistryName().toString().length(); this.insects.charAt(i - 1) != '}'; ++ i) {
                target.append(this.insects.charAt(i));
            }
            StringBuilder replacement = new StringBuilder(target);
            replacement.insert(1, variant + ",");
            this.insects = this.insects.replace(target.toString(), replacement.toString());
        }
    }
    @Override
    public boolean hasInsect(EntityType<? extends InsectEntity> insect) {
        return this.insects.contains(insect.getRegistryName().toString());
    }
    @Override
    public boolean hasVariant(EntityType<? extends InsectEntity> insect, int variant) {
        if(!this.hasInsect(insect)) {
            return false;
        }
        String numberChar = "";
        int number;
        for(int i = this.insects.indexOf(insect.getRegistryName().toString()) + insect.getRegistryName().toString().length(); this.insects.charAt(i - 1) != '}'; ++ i) {
            if(Character.isDigit(this.insects.charAt(i))) {
                numberChar += this.insects.charAt(i);
            } else {
                try {
                    number = Integer.parseInt(numberChar);
                    if(number == variant) {
                        return true;
                    }
                    numberChar = "";
                } catch(final NumberFormatException exception) { }
            }
        }
        return false;
    }
}