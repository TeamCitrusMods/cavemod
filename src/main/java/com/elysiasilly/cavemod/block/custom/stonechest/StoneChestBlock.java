package com.elysiasilly.cavemod.block.custom.stonechest;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class StoneChestBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SHAPE_OPEN = Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);

    public static final MapCodec<BarrelBlock> CODEC = simpleCodec(BarrelBlock::new);

    public static final BooleanProperty SEALED = BooleanProperty.create("sealed");


    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(SEALED) ? SHAPE : SHAPE_OPEN;
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state) {
        if(state.getValue(SEALED)) {
            level.setBlock(pos, this.defaultBlockState().setValue(SEALED, false), 4);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!state.getValue(SEALED)) {

            BlockEntity blockentity = level.getBlockEntity(pos);

            if (blockentity instanceof StoneChestBlockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, pos, (StoneChestBlockEntity)blockentity);
                }

                super.onRemove(state, level, pos, newState, isMoving);
                //pLevel.updateNeighbourForOutputSignal(pPos, this);
            } else {
                super.onRemove(state, level, pos, newState, isMoving);
            }
        }
    }

    @Override
    public @NotNull MapCodec<BarrelBlock> codec() {
        return CODEC;
    }

    public StoneChestBlock() {
        super(Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE));
        this.registerDefaultState(this.stateDefinition.any().setValue(SEALED, true));
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {

        if(pState.getValue(SEALED)) {
            return InteractionResult.FAIL;
        } {
            if (pLevel.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockEntity blockentity = pLevel.getBlockEntity(pPos);
                if (blockentity instanceof StoneChestBlockEntity) {
                    pPlayer.openMenu((StoneChestBlockEntity)blockentity);
                    pPlayer.awardStat(Stats.OPEN_BARREL);
                    PiglinAi.angerNearbyPiglins(pPlayer, true);
                }

                return InteractionResult.CONSUME;
            }
        }
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof StoneChestBlockEntity) {
            ((StoneChestBlockEntity)blockentity).recheckOpen();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StoneChestBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    /**
     * @deprecated call via {@link BlockStateBase#hasAnalogOutputSignal} whenever possible. Implementing/overriding is fine.
     */
    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    /**
     * Returns the analog signal this block emits. This is the signal a comparator can read from it.
     *
     * @deprecated call via {@link BlockStateBase#getAnalogOutputSignal} whenever possible. Implementing/overriding is fine.
     */
    @Override
    protected int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SEALED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(SEALED, true);
    }
}
