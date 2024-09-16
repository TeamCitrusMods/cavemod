package com.elysiasilly.cavemod.item.lanternbulbslice;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LanternBulbSliceItem extends Item implements ProjectileItem{


    public LanternBulbSliceItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            LanternBulbSliceEntity projectile = new LanternBulbSliceEntity(level, player);
            projectile.setItem(heldStack);

            //System.out.println(player.getXRot() + " " + player.getYRot());

            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 2);
            level.addFreshEntity(projectile);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            heldStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide());
    }


    @Override
    public @NotNull Projectile asProjectile(@NotNull Level pLevel, Position pPos, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        LanternBulbSliceEntity slice = new LanternBulbSliceEntity(pLevel, pPos.x(), pPos.y(), pPos.z(), 1);
        slice.setItem(pStack);
        return slice;
    }
}
