package io.github.fallOut015.entomology.item;

import io.github.fallOut015.entomology.MainEntomology;
import io.github.fallOut015.entomology.block.BlocksEntomology;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsEntomology {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainEntomology.MODID);



    public static final RegistryObject<Item> TERRARIUM = ITEMS.register("terrarium", () -> new BlockItem(BlocksEntomology.TERRARIUM.get(), new Item.Properties().tab(ItemGroupEntomology.TAB_BUGS)));
    public static final RegistryObject<Item> BUG_JAR = ITEMS.register("bug_jar", () -> new BlockItem(BlocksEntomology.BUG_JAR.get(), new Item.Properties().tab(ItemGroupEntomology.TAB_BUGS)));
    public static final RegistryObject<Item> ENTOMOLOGY_GUIDE = ITEMS.register("entomology_guide", () -> new EntomologyGuideItem(new Item.Properties().tab(ItemGroupEntomology.TAB_BUGS).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BUG_NET = ITEMS.register("bug_net", () -> new BugNetItem(new Item.Properties().tab(ItemGroupEntomology.TAB_BUGS).stacksTo(1).durability(150))); // make stay in crafting grid



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}