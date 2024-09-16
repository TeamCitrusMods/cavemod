package com.elysiasilly.cavemod.block.basic;

import com.elysiasilly.cavemod.block.basic.interfaces.BurnableTransformBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BurnTransformBlock extends Block implements BurnableTransformBlock {

    public BurnTransformBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Block getBlockToTransformInto(BlockState blockState) {
        return Blocks.ACACIA_PLANKS;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }
}
