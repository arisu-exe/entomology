package io.github.fallOut015.entomology.item;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ItemModelPropertiesEntomology {
    public static void doClientStuff(final FMLClientSetupEvent event) {
        ItemModelsProperties.register(ItemsEntomology.BUG_NET.get(), new ResourceLocation("entomology", "insect"), (stack, world, entity) -> {
            if(stack.getOrCreateTag().getString("InsectEntity").equals("entomology:butterfly")) {
                return 1;
            } else if(stack.getOrCreateTag().getString("InsectEntity").equals("entomology:firefly")) {
                return 2;
            }
            return 0;
        });
        ItemModelsProperties.register(ItemsEntomology.BUG_NET.get(), new ResourceLocation("entomology", "variant"), (stack, world, entity) -> {
            if(((CompoundNBT) (stack.getOrCreateTag().get("Data"))).contains("VARIANT")) {
                return ((CompoundNBT) (stack.getOrCreateTag().get("Data"))).getInt("VARIANT");
            }
            return 0;
        });
    }
}