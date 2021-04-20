package io.github.fallOut015.entomology.entity.insect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FireflyEntity extends InsectEntity implements IFlyingAnimal {
    private static final DataParameter<Float> SIZE = EntityDataManager.defineId(FireflyEntity.class, DataSerializers.FLOAT);

    public FireflyEntity(EntityType<? extends FireflyEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute applyAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).add(Attributes.FLYING_SPEED, (double)0.075F);
    }

    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
        return super.checkSpawnRules(p_213380_1_, p_213380_2_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 2.0f, 1.0D, 1.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1));
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(SIZE, 1.0f);

        super.defineSynchedData();
    }
    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);

        this.entityData.set(SIZE, nbt.getFloat("SIZE"));
    }
    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putFloat("SIZE", this.entityData.get(SIZE).floatValue());
    }
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setSize((this.random.nextFloat() * 0.25f) + 0.5f);

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new FlyingPathNavigator(this, worldIn);
    }

    @Override
    public boolean hasVariants() {
        return false;
    }
    @Override
    public int getVariant() {
        return 0;
    }
    @Override
    public void setVariant(int value) {
    }
    @Override
    public int getVariantsCount() {
        return 0;
    }
    @Override
    public ITextComponent getVariantName() {
        return (ITextComponent) TextComponent.EMPTY;
    }

    @Override
    public boolean hasSize() {
        return true;
    }
    @Override
    public float getSize() {
        return this.entityData.get(SIZE).floatValue();
    }
    @Override
    public void setSize(float value) {
        this.entityData.set(SIZE, value);
    }

    @Override
    public ITextComponent getFullName() {
        return this.getDisplayName();
    }

    @Override
    public int getU() {
        return 167;
    }
    @Override
    public int getV() {
        return 18;
    }
}