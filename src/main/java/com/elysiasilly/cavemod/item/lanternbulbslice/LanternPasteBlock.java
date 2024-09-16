package com.elysiasilly.cavemod.item.lanternbulbslice;

import com.elysiasilly.cavemod.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LanternPasteBlock extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_DOWN  = Block.box(0.0 , 15.0, 0.0 , 16.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_UP    = Block.box(0.0 , 0.0 , 0.0 , 16.0, 1.0 , 16.0);
    private static final VoxelShape SHAPE_EAST  = Block.box(0.0 , 0.0 , 0.0 , 1.0 , 16.0, 16.0);
    private static final VoxelShape SHAPE_WEST  = Block.box(15.0, 0.0 , 0.0 , 16.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_SOUTH = Block.box(0.0 , 0.0 , 0.0 , 16.0, 16.0, 1.0 );
    private static final VoxelShape SHAPE_NORTH = Block.box(0.0 , 0.0 , 15.0, 16.0, 16.0, 16.0);

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


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

    public LanternPasteBlock() {
        super(Properties.of()
                .lightLevel((state) -> 12)
                .noOcclusion()
                .noCollission()
                .pushReaction(PushReaction.DESTROY)
                .replaceable()
                .instabreak()
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
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

    @Override
    public @NotNull Item asItem() {
        return ItemRegistry.LANTERN_BULB_SLICE.asItem();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (facing.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

        IntProvider helpme = UniformInt.of(1, 2);

        RandomSource randomsource = level.random;

        Supplier<Vec3> supplier = () -> new Vec3(
                Mth.nextDouble(randomsource, 0, 0),
                Mth.nextDouble(randomsource, 0, 0),
                Mth.nextDouble(randomsource, 0, 0)
        );
        ParticleUtils.spawnParticlesOnBlockFace(level, pos.relative(state.getValue(FACING).getOpposite()), ParticleTypes.GLOW, helpme, state.getValue(FACING), supplier, 0.5);

        //ParticleUtils.spawnParticleOnFace(level, pos, state.getValue(FACING), ParticleTypes.GLOW_SQUID_INK, Vec3.fromRGB24(0), 1);
    }
}
