package io.github.fallOut015.entomology.common.capabilities;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class InsectTrackerProvider implements ICapabilityProvider {
    final IInsectTracker insectTracker;

    public InsectTrackerProvider(final IInsectTracker insectTracker) {
        this.insectTracker = insectTracker;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == CapabilitiesEntomology.INSECT_TRACKER) {
            return (LazyOptional<T>) LazyOptional.of(() -> this.insectTracker);
        }
        return null;
    }
}