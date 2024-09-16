package com.elysiasilly.cavemod.block.basic.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BasicDirectionalBlock extends Block {

    public BasicDirectionalBlock() {
        super(Properties.of()
                .noOcclusion()
                .noCollission()
                .pushReaction(PushReaction.DESTROY)
                .replaceable()
                .instabreak()
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP)
        );
    }

    private static final VoxelShape SHAPE_DOWN  = Block.box(0.0 , 15.0, 0.0 , 16.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_UP    = Block.box(0.0 , 0.0 , 0.0 , 16.0, 1.0 , 16.0);
    private static final VoxelShape SHAPE_EAST  = Block.box(0.0 , 0.0 , 0.0 , 1.0 , 16.0, 16.0);
    private static final VoxelShape SHAPE_WEST  = Block.box(15.0, 0.0 , 0.0 , 16.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_SOUTH = Block.box(0.0 , 0.0 , 0.0 , 16.0, 16.0, 1.0 );
    private static final VoxelShape SHAPE_NORTH = Block.box(0.0 , 0.0 , 15.0, 16.0, 16.0, 16.0);

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext collisionContext) {
        return switch (state.getValue(FACING)) {
            case UP    -> SHAPE_UP;
            case DOWN  -> SHAPE_DOWN;
            case WEST  -> SHAPE_WEST;
            case EAST  -> SHAPE_EAST;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getClickedFace());
    }

    private boolean canAttachTo(BlockGetter blockGetter, BlockPos pos, Direction direction) {
        BlockState blockstate = blockGetter.getBlockState(pos);
        return blockstate.isFaceSturdy(blockGetter, pos, direction);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(level, pos.relative(direction.getOpposite()), direction);
    }
}
