package io.github.fallOut015.entomology.block;

import io.github.fallOut015.entomology.MainEntomology;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksEntomology {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainEntomology.MODID);



    public static final RegistryObject<Block> ANTHILL = BLOCKS.register("anthill", () -> new Block(AbstractBlock.Properties.of(Material.DIRT)));
    public static final RegistryObject<Block> TERRARIUM = BLOCKS.register("terrarium", () -> new TerrariumBlock(AbstractBlock.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> BUG_JAR = BLOCKS.register("bug_jar", () -> new BugJarBlock(AbstractBlock.Properties.copy(Blocks.GLASS).noOcclusion().lightLevel(state -> state.getValue(BugJarBlock.HAS_FIREFLY).booleanValue() ? 5 : 0)));
    // bug jar? glass jar?



    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}