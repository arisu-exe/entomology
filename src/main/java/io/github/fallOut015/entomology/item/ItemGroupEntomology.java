package io.github.fallOut015.entomology.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupEntomology {
    public static final ItemGroup TAB_BUGS = new ItemGroup("bugs") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ItemsEntomology.BUG_NET.get());
        }
    };
}