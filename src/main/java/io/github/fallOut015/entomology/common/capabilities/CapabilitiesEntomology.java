package io.github.fallOut015.entomology.common.capabilities;

import io.github.fallOut015.entomology.MainEntomology;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CapabilitiesEntomology {
    @CapabilityInject(IInsectTracker.class)
    public static final Capability<IInsectTracker> INSECT_TRACKER = null;

    public static void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IInsectTracker.class, new InsectTrackerStorage(), new InsectTrackerCallable());
    }

    public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MainEntomology.MODID, event.getObject().getUUID().toString()), new InsectTrackerProvider(new InsectTracker()));
        }
    }
}