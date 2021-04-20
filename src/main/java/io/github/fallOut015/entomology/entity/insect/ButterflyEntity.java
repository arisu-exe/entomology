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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ButterflyEntity extends InsectEntity implements IFlyingAnimal {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(ButterflyEntity.class, DataSerializers.INT);
    private static final DataParameter<Float> SIZE = EntityDataManager.defineId(ButterflyEntity.class, DataSerializers.FLOAT);

    public static String[] VARIANTS = { "black_yellow", "blue_black", "red_black", "white_black" };

    public ButterflyEntity(EntityType<? extends ButterflyEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute applyAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).add(Attributes.FLYING_SPEED, (double)0.3F);
    }

    @Override
    protected void registerGoals() {
        // find flower
        this.goalSelector.addGoal(2, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 2.0f, 1.0D, 1.0D));
        // follow other butterflies
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1));
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(VARIANT, 0);
        this.entityData.define(SIZE, 1.0f);

        super.defineSynchedData();
    }
    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);

        this.entityData.set(VARIANT, nbt.getInt("VARIANT"));
        this.entityData.set(SIZE, nbt.getFloat("SIZE"));
    }
    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putInt("VARIANT", this.entityData.get(VARIANT).intValue());
        nbt.putFloat("SIZE", this.entityData.get(SIZE).floatValue());
    }
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setVariant(this.random.nextInt(VARIANTS.length));
        this.setSize((this.random.nextFloat() * 0.25f) + 0.75f);

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new FlyingPathNavigator(this, worldIn);
    }

    @Override
    public void tick() {
        int groundDistance = 0;
        BlockPos pos = this.blockPosition();
        while(this.level.isEmptyBlock(pos)) {
            ++ groundDistance;
            pos = pos.below();
        }

        if(groundDistance < 2/* + this.fallDistance*/) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.2f, 0));
            if(this.getDeltaMovement().y() > 0.4f) {
                this.setDeltaMovement(this.getDeltaMovement().x(), 0.4f, this.getDeltaMovement().z());
            }
        }
        if(this.getDeltaMovement().y() < -0.4f) {
            this.setDeltaMovement(this.getDeltaMovement().x(), -0.4f, this.getDeltaMovement().z());
        }

        super.tick();
    }

    @Override
    public boolean hasVariants() {
        return true;
    }
    @Override
    public int getVariant() {
        return this.entityData.get(VARIANT).intValue();
    }
    @Override
    public void setVariant(int value) {
        this.entityData.set(VARIANT, value);
    }
    @Override
    public int getVariantsCount() {
        return VARIANTS.length;
    }
    @Override
    public ITextComponent getVariantName() {
        return new TranslationTextComponent("entity.entomology.butterfly." + VARIANTS[this.getVariant()]);
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
        return ((TranslationTextComponent) this.getVariantName()).append(" ").append(this.getDisplayName());
    }

    @Override
    public int getU() {
        return this.getVariant() * 17 + 167;
    }
    @Override
    public int getV() {
        return 1;
    }
}