package com.elysiasilly.cavemod.block.custom;

import com.elysiasilly.cavemod.item.lanternbulbslice.LanternBulbSliceEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class InfestedBraidedRootBlock extends Block {
    public InfestedBraidedRootBlock() {
        super(Properties.ofFullCopy(Blocks.MELON));
    }

    @Override
    protected void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !EnchantmentHelper.hasTag(pStack, EnchantmentTags.PREVENTS_INFESTED_SPAWNS)) {
            this.spawnInfestation(pLevel, pPos);
        }
    }

    private void spawnInfestation(ServerLevel pLevel, BlockPos pPos) {

        for (int i = 0; i < 5; i++) {
            Bat bat = EntityType.BAT.create(pLevel);

            if (bat != null) {
                bat.moveTo((double)pPos.getX() + 0.5, (double)pPos.getY(), (double)pPos.getZ() + 0.5, 0.0F, 0.0F);
                pLevel.addFreshEntity(bat);
                bat.spawnAnim();
            }
        }
    }
}
