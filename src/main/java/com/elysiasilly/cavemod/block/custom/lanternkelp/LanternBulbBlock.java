package com.elysiasilly.cavemod.block.custom.lanternkelp;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LanternBulbBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public LanternBulbBlock() {
        super(
                Properties.ofFullCopy(Blocks.MELON)
                        .lightLevel( (state) -> state.getValue(ACTIVE) ? 12 : 0)
        );

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ACTIVE,false)
        );
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // not able to figure it out right now but, change this at some point in the future to be activation upon release, mob touches it and once no more touch it activates, some for the tumorous bulb

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

        if(!state.getValue(ACTIVE) && !level.isClientSide) {

            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));

        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(ACTIVE);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(ACTIVE)) {
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, false));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.destroyBlock(pos, true);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(state.getValue(ACTIVE)) {
            ParticleUtils.spawnParticleInBlock(level, pos, 10, ParticleTypes.GLOW);
        }
    }
}
