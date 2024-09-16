package com.elysiasilly.cavemod.block.custom.lanternkelp;

import com.elysiasilly.cavemod.block.basic.CMGrowingPlantHeadBlock;
import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class LanternKelpBlock extends CMGrowingPlantHeadBlock implements LiquidBlockContainer {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    public LanternKelpBlock() {
        super(
                Properties.ofFullCopy(Blocks.KELP),
                Direction.UP,
                SHAPE,
                true,
                0.14
        );
    }

    //public MapCodec<KelpBlock> codec() {return CODEC;}

    @Override
    protected boolean canGrowInto(BlockState pState) {
        return pState.is(Blocks.WATER);
    }

    @Override
    protected Block getFruitBlock() {
        return BlockRegistry.LANTERN_KELP_BULB.get();
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.LANTERN_KELP_BODY.get();
    }

    @Override
    public boolean canAttachTo(BlockState pState) {
        return !pState.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player pPlayer, BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return false;
    }

    @Override
    public int maxAge() {
        return 8;
    }

    @Override
    public boolean bearsFruit() {
        return true;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return 1;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(pContext) : null;
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        BlockPos posGrowthDirection = pos.relative(GROWTH_DIRECTION);

        boolean canGrowInto = canGrowInto(level.getBlockState(posGrowthDirection));
        boolean canCropGrow = net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos.relative(GROWTH_DIRECTION), state, random.nextDouble() < 0.14);

        if(canGrowInto && canCropGrow) {
            if (state.getValue(AGE) == maxAge() && bearsFruit()) {
                int chanceForRot = level.getRandom().nextIntBetweenInclusive(1, 10);
                if(chanceForRot == 2) {
                    level.setBlockAndUpdate(posGrowthDirection, BlockRegistry.ROTTING_LANTERN_KELP_BULB.get().defaultBlockState());
                } else {
                    level.setBlockAndUpdate(posGrowthDirection, BlockRegistry.LANTERN_KELP_BULB.get().defaultBlockState());
                }
                FireCropGrowPost(level, posGrowthDirection);
            } else {
                level.setBlockAndUpdate(posGrowthDirection, state.cycle(AGE));
                FireCropGrowPost(level, posGrowthDirection);
            }
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {

        if(bearsFruit() && facing == GROWTH_DIRECTION) {
            if(facingState.is(BlockRegistry.LANTERN_KELP_BULB.get()) || facingState.is(BlockRegistry.ROTTING_LANTERN_KELP_BULB.get())) {
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
                level.scheduleTick(pos.relative(GROWTH_DIRECTION), BlockRegistry.LANTERN_KELP_BULB.get(), 1);
                level.scheduleTick(pos.relative(GROWTH_DIRECTION), BlockRegistry.ROTTING_LANTERN_KELP_BULB.get(), 1);
            }
        }
    }
}
