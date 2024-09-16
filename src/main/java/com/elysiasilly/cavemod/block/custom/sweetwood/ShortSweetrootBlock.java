package com.elysiasilly.cavemod.block.custom.sweetwood;

import com.elysiasilly.cavemod.block.basic.ShortGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ShortSweetrootBlock extends ShortGrassBlock {

    public ShortSweetrootBlock(Block tallVariant) {
        super(tallVariant);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState belowBlockState = level.getBlockState(blockpos);
        return  belowBlockState.is(Blocks.CLAY);
    }
}
