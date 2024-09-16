package com.elysiasilly.cavemod.block.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public abstract class CMGrowingPlantBlock extends Block {

    protected final Direction GROWTH_DIRECTION;
    protected final boolean FLUID_TICKS;
    protected final VoxelShape SHAPE;

    protected CMGrowingPlantBlock(Properties properties, Direction growthDirection, VoxelShape shape, boolean fluidTicks
    ) {
        super(properties);
        GROWTH_DIRECTION = growthDirection;
        SHAPE = shape;
        FLUID_TICKS = fluidTicks;
    }

    // protected abstract MapCodec<? extends CMGrowingPlantBlock> codec();

    protected abstract int getBlocksToGrowWhenBonemealed(RandomSource randomSource);

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(GROWTH_DIRECTION));
        return !blockstate.is(getHeadBlock()) && !blockstate.is(getBodyBlock())
                ? getStateForPlacement(context.getLevel())
                : getBodyBlock().defaultBlockState();
    }

    public BlockState getStateForPlacement(LevelAccessor pLevel) {
        return defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.relative(GROWTH_DIRECTION.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return canAttachTo(blockstate) && (blockstate.is(getHeadBlock()) || blockstate.is(getBodyBlock()) || blockstate.isFaceSturdy(level, blockpos, GROWTH_DIRECTION));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    public boolean canAttachTo(BlockState state) {return true;}

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {return SHAPE;}

    protected abstract CMGrowingPlantHeadBlock getHeadBlock();

    protected abstract boolean canGrowInto(BlockState state);

    protected abstract Block getBodyBlock();
}
