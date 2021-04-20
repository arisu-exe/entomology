package io.github.fallOut015.entomology.entity.insect;

import io.github.fallOut015.entomology.client.gui.toasts.DiscoveryToast;
import io.github.fallOut015.entomology.common.capabilities.CapabilitiesEntomology;
import io.github.fallOut015.entomology.item.ItemsEntomology;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public abstract class InsectEntity extends CreatureEntity {
    protected InsectEntity(EntityType<? extends InsectEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    @Override
    public boolean canCollideWith(Entity entityIn) {
        return false;
    }
    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }
    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(stack.getItem() == ItemsEntomology.BUG_NET.get()) {
            CompoundNBT nbt = player.getItemInHand(hand).getOrCreateTag();
            if(!nbt.contains("InsectEntity")) {
                nbt.putString("InsectEntity", this.getType().getRegistryName().toString());
                nbt.putString("Name", this.getFullName().getString());
                nbt.put("Data", this.serializeNBT());

                player.getCapability(CapabilitiesEntomology.INSECT_TRACKER).ifPresent(insectTracker -> {
                    if(this.hasVariants()) {
                        if(insectTracker.hasInsect((EntityType<? extends InsectEntity>) this.getType())) {
                            if(!insectTracker.hasVariant((EntityType<? extends InsectEntity>) this.getType(), this.getVariant())) {
                                insectTracker.addVariant((EntityType<? extends InsectEntity>) this.getType(), this.getVariant());
                                if(this.level.isClientSide) {
                                    Minecraft.getInstance().getToasts().addToast(new DiscoveryToast(this));
                                }
                            }
                        } else {
                            insectTracker.addInsect((EntityType<? extends InsectEntity>) this.getType());
                            insectTracker.addVariant((EntityType<? extends InsectEntity>) this.getType(), this.getVariant());
                            if(this.level.isClientSide) {
                                Minecraft.getInstance().getToasts().addToast(new DiscoveryToast(this));
                            }
                        }
                    } else if(!insectTracker.hasInsect((EntityType<? extends InsectEntity>) this.getType())) {
                        insectTracker.addInsect((EntityType<? extends InsectEntity>) this.getType());
                        if(this.level.isClientSide) {
                            Minecraft.getInstance().getToasts().addToast(new DiscoveryToast(this));
                        }
                    }
                });

                stack.hurtAndBreak(1, player, playerEntity -> {
                    playerEntity.broadcastBreakEvent(hand);
                });

                this.remove();
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    public abstract boolean hasVariants();
    public abstract int getVariant();
    public abstract void setVariant(int value);
    public abstract int getVariantsCount();
    public abstract ITextComponent getVariantName();

    public abstract boolean hasSize();
    public abstract float getSize();
    public abstract void setSize(float value);

    public abstract ITextComponent getFullName();

    public abstract int getU();
    public abstract int getV();
}