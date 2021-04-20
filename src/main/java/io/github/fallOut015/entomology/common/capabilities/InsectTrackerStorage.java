package io.github.fallOut015.entomology.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class InsectTrackerStorage implements Capability.IStorage<IInsectTracker> {
    @Override
    public INBT writeNBT(Capability<IInsectTracker> capability, IInsectTracker instance, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("insectTracker", instance.getCollectedInsects());
        return nbt;
    }
    @Override
    public void readNBT(Capability<IInsectTracker> capability, IInsectTracker instance, Direction side, INBT nbt) {
        if(nbt instanceof CompoundNBT) {
            instance.setCollectedInsects(((CompoundNBT) nbt).getString("insectTracker"));
        }
    }
}