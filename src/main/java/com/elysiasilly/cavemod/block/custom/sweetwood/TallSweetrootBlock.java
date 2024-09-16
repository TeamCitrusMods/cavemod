package com.elysiasilly.cavemod.block.custom.sweetwood;

import com.elysiasilly.cavemod.block.basic.TallGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class TallSweetrootBlock extends TallGrassBlock {

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {

        BlockPos blockpos = pos.below();
        BlockState belowBlockState = level.getBlockState(blockpos);

        if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return  belowBlockState.is(Blocks.CLAY);
        } {
            return belowBlockState.is(this);
        }

    }
}
