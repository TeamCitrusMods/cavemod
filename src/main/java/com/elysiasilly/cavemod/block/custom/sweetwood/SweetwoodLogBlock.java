package com.elysiasilly.cavemod.block.custom.sweetwood;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SweetwoodLogBlock extends PipeBlock implements SimpleWaterloggedBlock {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final MapCodec<SweetwoodLogBlock> CODEC = simpleCodec(SweetwoodLogBlock::new);

    public SweetwoodLogBlock(Properties properties) {
        super(0.3125F, properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false)
                        .setValue(UP, false)
                        .setValue(DOWN, false)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<SweetwoodLogBlock> codec() {return CODEC;}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getStateWithConnections(pContext.getLevel(), pContext.getClickedPos(), this.defaultBlockState());
    }

    public static BlockState getStateWithConnections(BlockGetter level, BlockPos pos, BlockState state) {

        BlockState posBelow = getState(level, pos.below());
        BlockState posAbove = getState(level, pos.above());
        BlockState posNorth = getState(level, pos.north());
        BlockState posEast  = getState(level, pos.east());
        BlockState posSouth = getState(level, pos.south());
        BlockState posWest  = getState(level, pos.west());

        return state
                .trySetValue(DOWN,  WoodLogic(posBelow, null) || posBelow.is(Blocks.CLAY))
                .trySetValue(UP,    WoodLogic(posAbove, null) || posAbove.is(Blocks.AZALEA_LEAVES))
                .trySetValue(NORTH, WoodLogic(posNorth, posBelow) || leavesLogic(posNorth, posAbove) || clayLogic(posNorth, posBelow))
                .trySetValue(EAST,  WoodLogic(posEast, posBelow)  || leavesLogic(posEast, posAbove)  || clayLogic(posEast, posBelow))
                .trySetValue(SOUTH, WoodLogic(posSouth, posBelow) || leavesLogic(posSouth, posAbove) || clayLogic(posSouth, posBelow))
                .trySetValue(WEST,  WoodLogic(posWest, posBelow)  || leavesLogic(posWest, posAbove)  || clayLogic(posWest, posBelow));
    }

    public static boolean leavesLogic(BlockState block, BlockState block2) {

        return (block.is(Blocks.AZALEA_LEAVES) && !block2.is(Blocks.AZALEA_LEAVES));
    }

    public static boolean clayLogic(BlockState block, BlockState block2) {
        return (block.is(Blocks.CLAY) && !block2.is(Blocks.CLAY));
    }

    public static boolean WoodLogic(BlockState state, BlockState block2) {

        boolean adjacentIsSweetwood =
                state.is(BlockRegistry.SWEETWOOD_LOG.get()) || state.is(BlockRegistry.STRIPPED_SWEETWOOD_LOG.get()) || state.is(BlockRegistry.SWEETWOOD_BUNDLE.get()) || state.is(BlockRegistry.STRIPPED_SWEETWOOD_BUNDLE.get());

        if(block2 == null) {
            return adjacentIsSweetwood;
        } {
            boolean belowIsNotSweetwood =
                    !block2.is(BlockRegistry.SWEETWOOD_LOG.get()) && !block2.is(BlockRegistry.STRIPPED_SWEETWOOD_LOG.get());
            return belowIsNotSweetwood && adjacentIsSweetwood;
        }

        /*
        if(block2 != null) {
            return adjacentIsSweetwood && belowIsNotSweetwood;
        } {
            return adjacentIsSweetwood;
        }

         */
        /*
        return (state.is(CaveBlocks.SWEETWOOD_LOG.get()) || state.is(CaveBlocks.STRIPPED_SWEETWOOD_LOG.get()) || state.is(CaveBlocks.SWEETWOOD_BUNDLE.get()) || state.is(CaveBlocks.STRIPPED_SWEETWOOD_BUNDLE.get())
        && !block2.is(CaveBlocks.SWEETWOOD_LOG.get()) && !block2.is(CaveBlocks.STRIPPED_SWEETWOOD_LOG.get())

        );
         */
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {

        if(facingState.is(this)) {

            boolean flag = facingState.is(this) || facing == Direction.DOWN && facingState.is(Blocks.CLAY);
            return state.setValue(PROPERTY_BY_DIRECTION.get(facing), flag);

        }

        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return state;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static BlockState getState(BlockGetter level, BlockPos pos) {return level.getBlockState(pos);}

    /*
    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {

        if (context.getItemInHand().getItem() instanceof AxeItem && state.is(CaveBlocks.SWEETWOOD_LOG.get())) {

            return CaveBlocks.STRIPPED_SWEETWOOD_LOG.get().defaultBlockState()
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                    .setValue(NORTH, state.getValue(NORTH))
                    .setValue(EAST, state.getValue(EAST))
                    .setValue(SOUTH, state.getValue(SOUTH))
                    .setValue(WEST, state.getValue(WEST))
                    .setValue(UP, state.getValue(UP))
                    .setValue(DOWN, state.getValue(DOWN));
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

     */

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {

        if (context.getItemInHand().getItem() instanceof AxeItem && state.is(BlockRegistry.SWEETWOOD_LOG.get())) {

            return BlockRegistry.STRIPPED_SWEETWOOD_LOG.get().defaultBlockState()
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                    .setValue(NORTH, state.getValue(NORTH))
                    .setValue(EAST, state.getValue(EAST))
                    .setValue(SOUTH, state.getValue(SOUTH))
                    .setValue(WEST, state.getValue(WEST))
                    .setValue(UP, state.getValue(UP))
                    .setValue(DOWN, state.getValue(DOWN));
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
