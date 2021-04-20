package io.github.fallOut015.entomology.item;

import io.github.fallOut015.entomology.client.gui.screen.inventory.EntomologyGuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EntomologyGuideItem extends Item {
    EntomologyGuideItem(Item.Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(player instanceof ClientPlayerEntity) {
            Minecraft.getInstance().setScreen(new EntomologyGuideScreen((ClientPlayerEntity) player));
        }
        player.awardStat(Stats.ITEM_USED.get(this));

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }
}