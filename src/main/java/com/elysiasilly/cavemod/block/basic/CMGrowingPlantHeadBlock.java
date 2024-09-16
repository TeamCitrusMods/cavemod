package com.elysiasilly.cavemod.block.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class CMGrowingPlantHeadBlock extends CMGrowingPlantBlock implements BonemealableBlock {

    public abstract int maxAge();
    public abstract boolean bearsFruit();
    private final double GROW_TICK_PROBABILITY;

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 20);
    public static final BooleanProperty BULB = BooleanProperty.create("bulb");

    protected CMGrowingPlantHeadBlock(Properties properties, Direction growthDirection, VoxelShape shape, boolean scheduleFluidTicks, double growPerTickProbability) {
        super(properties, growthDirection, shape, scheduleFluidTicks);
        GROW_TICK_PROBABILITY = growPerTickProbability;
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
        if (bearsFruit()) {registerDefaultState(stateDefinition.any().setValue(BULB, false));}
    }

    @Override
    public BlockState getStateForPlacement(LevelAccessor level) {
        if (bearsFruit()) {
            return defaultBlockState().setValue(BULB, false).setValue(AGE, level.getRandom().nextIntBetweenInclusive(0, maxAge() - 4));
        } else {
            return defaultBlockState().setValue(AGE, level.getRandom().nextIntBetweenInclusive(0, maxAge() - 4));
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return bearsFruit() ? !state.getValue(BULB) : state.getValue(AGE) != maxAge();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        BlockPos posGrowthDirection = pos.relative(GROWTH_DIRECTION);

        boolean canGrowInto = canGrowInto(level.getBlockState(posGrowthDirection));
        boolean canCropGrow = net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos.relative(GROWTH_DIRECTION), state, random.nextDouble() < GROW_TICK_PROBABILITY);

        if(canGrowInto && canCropGrow) {
            if (state.getValue(AGE) == maxAge() && bearsFruit()) {
                level.setBlockAndUpdate(posGrowthDirection, getFruitBlock().defaultBlockState());
                FireCropGrowPost(level, posGrowthDirection);
            } else {
                level.setBlockAndUpdate(posGrowthDirection, state.cycle(AGE));
                FireCropGrowPost(level, posGrowthDirection);
            }
        }
    }

    public void FireCropGrowPost(Level level, BlockPos posGrowthDirection) {
        net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, posGrowthDirection, level.getBlockState(posGrowthDirection));
    }


    protected BlockState updateBodyAfterConvertedFromHead(BlockState body) {
        return body;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {

        if(bearsFruit() && facing == GROWTH_DIRECTION) {
            if(facingState.is(getFruitBlock())) {
                System.out.println(true);
                level.setBlock(currentPos, state.setValue(BULB, true), 3);
            } else {
                System.out.println(false);
                level.setBlock(currentPos, state.setValue(BULB, false), 3);
            }
        }

        if (facing == GROWTH_DIRECTION.getOpposite() && !state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        if (facing != GROWTH_DIRECTION || !facingState.is(this) && !facingState.is(getBodyBlock())) {
            if (FLUID_TICKS) {
                level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return updateBodyAfterConvertedFromHead(getBodyBlock().defaultBlockState());
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            if(bearsFruit()) {
                level.scheduleTick(pos.relative(GROWTH_DIRECTION), getFruitBlock(), 1);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        if(bearsFruit()) {builder.add(BULB);}
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return canGrowInto(level.getBlockState(pos.relative(GROWTH_DIRECTION)));
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(GROWTH_DIRECTION);
        int i = Math.min(state.getValue(AGE) + 1, maxAge());
        int j = getBlocksToGrowWhenBonemealed(random);

        for (int k = 0; k < j && canGrowInto(level.getBlockState(blockpos)); k++) {
            level.setBlockAndUpdate(blockpos, state.setValue(AGE, i));
            blockpos = blockpos.relative(GROWTH_DIRECTION);
            i = Math.min(i + 1, maxAge());
        }
    }

    protected abstract int getBlocksToGrowWhenBonemealed(RandomSource pRandom);

    protected abstract boolean canGrowInto(BlockState pState);

    @Override
    protected CMGrowingPlantHeadBlock getHeadBlock() {return this;}

    protected abstract Block getFruitBlock();
}
