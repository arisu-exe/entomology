package io.github.fallOut015.entomology.item;

import io.github.fallOut015.entomology.block.BlocksEntomology;
import io.github.fallOut015.entomology.entity.insect.ButterflyEntity;
import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import io.github.fallOut015.entomology.tileentity.BugJarTileEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class BugNetItem extends Item {
    public BugNetItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        CompoundNBT tag = player.getItemInHand(context.getHand()).getOrCreateTag();
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        if(world.getBlockState(blockpos).getBlock() == BlocksEntomology.BUG_JAR.get()) {
            @Nullable BugJarTileEntity jar = (BugJarTileEntity) world.getBlockEntity(blockpos);
            if(jar != null) {
                if(jar.hasInsect() && !tag.contains("InsectEntity")) {
                    InsectEntity insect = jar.removeInsect();

                    tag.putString("InsectEntity", insect.getType().getRegistryName().toString());
                    tag.putString("Name", insect.getFullName().toString());
                    tag.put("Data", insect.serializeNBT());

                    return ActionResultType.SUCCESS;
                } else if(!jar.hasInsect() && tag.contains("InsectEntity")) {
                    InsectEntity insect;
                    if(world.isClientSide()) {
                        insect = (InsectEntity) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(tag.getString("InsectEntity"))).create(world);
                    } else {
                        insect = (InsectEntity) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(tag.getString("InsectEntity"))).create((ServerWorld) world, (CompoundNBT) tag.get("Data"), null, null, blockpos, SpawnReason.TRIGGERED, false, false);
                    }

                    if(tag.get("Data") instanceof CompoundNBT) {
                        if(((CompoundNBT) tag.get("Data")).contains("VARIANT") && insect.hasVariants()) {
                            insect.setVariant(((CompoundNBT) tag.get("Data")).getInt("VARIANT"));
                        }
                        if(((CompoundNBT) tag.get("Data")).contains("SIZE") && insect.hasSize()) {
                            insect.setSize(((CompoundNBT) tag.get("Data")).getFloat("SIZE"));
                        }
                    }

                    if(!player.isCreative()) {
                        tag.remove("InsectEntity");
                        tag.remove("Name");
                        tag.remove("Data");
                    }

                    jar.placeInsect(insect);

                    return ActionResultType.SUCCESS;
                } else {
                    return ActionResultType.PASS;
                }
            }
            return ActionResultType.FAIL;
        } else {
            if(tag.contains("InsectEntity") && world instanceof ServerWorld) {
                Direction direction = context.getClickedFace();
                if (!world.getBlockState(blockpos).getCollisionShape(world, blockpos).isEmpty()) {
                    blockpos = blockpos.relative(direction);
                }
                InsectEntity insect = (InsectEntity) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(tag.getString("InsectEntity"))).create((ServerWorld) world, (CompoundNBT) tag.get("Data"), null, null, blockpos, SpawnReason.TRIGGERED, false, false);

                if(tag.get("Data") instanceof CompoundNBT) {
                    if(((CompoundNBT) tag.get("Data")).contains("VARIANT") && insect.hasVariants()) {
                        insect.setVariant(((CompoundNBT) tag.get("Data")).getInt("VARIANT"));
                    }
                    if(((CompoundNBT) tag.get("Data")).contains("SIZE") && insect.hasSize()) {
                        insect.setSize(((CompoundNBT) tag.get("Data")).getFloat("SIZE"));
                    }
                }

                world.addFreshEntity(insect);

                if(!player.isCreative()) {
                    tag.remove("InsectEntity");
                    tag.remove("Name");
                    tag.remove("Data");
                }

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(stack.getOrCreateTag().contains("InsectEntity")) {
            String name = stack.getTag().getString("Name");
            tooltip.add(new StringTextComponent(name).withStyle(TextFormatting.ITALIC, TextFormatting.YELLOW));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
    @Override
    public ITextComponent getName(ItemStack stack) {
        if(stack.getOrCreateTag().contains("InsectEntity")) {
            return new TranslationTextComponent("item.entomology.bug_net.used");
        } else {
            return super.getName(stack);
        }
    }
    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(group == ItemGroupEntomology.TAB_BUGS || group == ItemGroup.TAB_SEARCH) {
            for(int variant = 0; variant < ButterflyEntity.VARIANTS.length; ++ variant) {
                ItemStack net = new ItemStack(ItemsEntomology.BUG_NET.get());
                net.getOrCreateTag().putString("InsectEntity", "entomology:butterfly");
                String name = new TranslationTextComponent("entity.entomology.butterfly." + ButterflyEntity.VARIANTS[variant]).append(" ").append(new TranslationTextComponent("entity.entomology.butterfly")).getString();
                net.getTag().putString("Name", name);
                CompoundNBT tag = new CompoundNBT();
                tag.putInt("VARIANT", variant);
                tag.putFloat("SIZE", 1.0f);
                net.getTag().put("Data", tag);
                items.add(net);
            }
            ItemStack net = new ItemStack(ItemsEntomology.BUG_NET.get());
            net.getOrCreateTag().putString("InsectEntity", "entomology:firefly");
            net.getTag().putString("Name", new TranslationTextComponent("entity.entomology.firefly").getString());
            CompoundNBT tag = new CompoundNBT();
            tag.putFloat("SIZE", 1.0f);
            net.getTag().put("Data", tag);
            items.add(net);
        }

        // TODO fix using untranslated name in search

        super.fillItemCategory(group, items);
    }
}