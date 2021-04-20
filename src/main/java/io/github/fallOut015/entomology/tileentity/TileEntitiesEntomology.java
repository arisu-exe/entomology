package io.github.fallOut015.entomology.tileentity;

import io.github.fallOut015.entomology.MainEntomology;
import io.github.fallOut015.entomology.block.BlocksEntomology;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntitiesEntomology {
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MainEntomology.MODID);



    public static final RegistryObject<TileEntityType<BugJarTileEntity>> BUG_JAR = TILE_ENTITIES.register("bug_jar", () -> TileEntityType.Builder.of(BugJarTileEntity::new, BlocksEntomology.BUG_JAR.get()).build(Util.fetchChoiceType(TypeReferences.BLOCK_ENTITY, "bug_jar")));



    public static void register(IEventBus bus) {
        TILE_ENTITIES.register(bus);
    }
}
