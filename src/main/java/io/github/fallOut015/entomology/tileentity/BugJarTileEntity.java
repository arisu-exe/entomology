package io.github.fallOut015.entomology.tileentity;

import io.github.fallOut015.entomology.block.BugJarBlock;
import io.github.fallOut015.entomology.entity.EntitiesEntomology;
import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class BugJarTileEntity extends TileEntity implements ITickableTileEntity {
    @Nullable InsectEntity insect;

    public BugJarTileEntity() {
        super(TileEntitiesEntomology.BUG_JAR.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        if(this.insect != null) {
            tag.putString("InsectEntity", this.getInsect().getType().getRegistryName().toString());
            tag.putString("Name", this.getInsect().getFullName().getString());
            tag.put("Data", this.getInsect().serializeNBT());
        }
        return super.save(tag);
    }
    @Override
    public void load(BlockState state, CompoundNBT tag) {
        if(tag.contains("InsectEntity")) {
            InsectEntity insect = (InsectEntity) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(tag.getString("InsectEntity"))).create((ServerWorld) this.level, (CompoundNBT) tag.get("Data"), null, null, this.getBlockPos().above(), SpawnReason.CHUNK_GENERATION, false, false);

            if(tag.get("Data") instanceof CompoundNBT) {
                if(((CompoundNBT) tag.get("Data")).contains("VARIANT") && insect.hasVariants()) {
                    insect.setVariant(((CompoundNBT) tag.get("Data")).getInt("VARIANT"));
                }
                if(((CompoundNBT) tag.get("Data")).contains("SIZE") && insect.hasSize()) {
                    insect.setSize(((CompoundNBT) tag.get("Data")).getFloat("SIZE"));
                }
            }

            this.placeInsect(insect);
        }
        super.load(state, tag);
    }
    @Override
    public void tick() {
        /*if(this.insect != null) {
            this.insect.tick();
        }*/
    }

    public boolean hasInsect() {
        return this.insect != null;
    }
    public @Nullable InsectEntity getInsect() {
        return this.insect;
    }
    public InsectEntity removeInsect() {
        InsectEntity insect2 = this.insect;
        this.insect = null;
        this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().getBlock().defaultBlockState());
        return insect2;
    }
    public void placeInsect(InsectEntity insect) {
        if(this.insect == null) {
            this.insect = insect;
            if(this.insect.getType() == EntitiesEntomology.FIREFLY.get()) {
                this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(BugJarBlock.HAS_FIREFLY, true));
            }
        }
    }
}