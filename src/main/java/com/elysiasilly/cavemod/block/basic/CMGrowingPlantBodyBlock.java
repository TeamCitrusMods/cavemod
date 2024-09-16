package com.elysiasilly.cavemod.block.basic;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public abstract class CMGrowingPlantBodyBlock extends CMGrowingPlantBlock implements BonemealableBlock {

    protected CMGrowingPlantBodyBlock(Properties pProperties, Direction pGrowthDirection, VoxelShape pShape, boolean pScheduleFluidTicks) {
        super(pProperties, pGrowthDirection, pShape, pScheduleFluidTicks
        );
    }

    //protected abstract MapCodec<? extends CMGrowingPlantBlock> codec();

    protected BlockState updateHeadAfterConvertedFromBody(BlockState pHead, BlockState pBody) {
        return pBody;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == GROWTH_DIRECTION.getOpposite() && !pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        CMGrowingPlantHeadBlock growingplantheadblock = this.getHeadBlock();
        if (pFacing == GROWTH_DIRECTION && !pFacingState.is(this) && !pFacingState.is(growingplantheadblock)) {
            return this.updateHeadAfterConvertedFromBody(pState, growingplantheadblock.getStateForPlacement(pLevel));
        } else {
            if (FLUID_TICKS) {
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }

            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(getHeadBlock());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        Optional<BlockPos> optional = this.getHeadPos(pLevel, pPos, pState.getBlock());
        return optional.isPresent() && this.getHeadBlock().canGrowInto(pLevel.getBlockState(optional.get().relative(GROWTH_DIRECTION)));
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        Optional<BlockPos> optional = this.getHeadPos(pLevel, pPos, pState.getBlock());
        if (optional.isPresent()) {
            BlockState blockstate = pLevel.getBlockState(optional.get());
            ((GrowingPlantHeadBlock)blockstate.getBlock()).performBonemeal(pLevel, pRandom, optional.get(), blockstate);
        }
    }

    private Optional<BlockPos> getHeadPos(BlockGetter pLevel, BlockPos pPos, Block pBlock) {
        return BlockUtil.getTopConnectedBlock(pLevel, pPos, pBlock, GROWTH_DIRECTION, this.getHeadBlock());
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        boolean flag = super.canBeReplaced(pState, pUseContext);
        return (!flag || !pUseContext.getItemInHand().is(this.getHeadBlock().asItem())) && flag;
    }

    @Override
    protected Block getBodyBlock() {
        return this;
    }
}
