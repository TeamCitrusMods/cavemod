package com.elysiasilly.cavemod.item;

import com.elysiasilly.cavemod.registry.ItemRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class SweetwoodBarkItem extends Item {

    public SweetwoodBarkItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }
        pEntityLiving.hurt(pLevel.damageSources().sweetBerryBush(), 1.0F);

        if (pEntityLiving instanceof Player player) {
            return ItemUtils.createFilledResult(pStack, player, new ItemStack(ItemRegistry.TOOTH.asItem()), false);
        } else {
            pStack.consume(1, pEntityLiving);
            return pStack;
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
        return 128;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }
}
