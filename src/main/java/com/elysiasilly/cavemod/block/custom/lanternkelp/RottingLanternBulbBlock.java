package com.elysiasilly.cavemod.block.custom.lanternkelp;

import com.elysiasilly.cavemod.item.lanternbulbslice.LanternBulbSliceEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RottingLanternBulbBlock extends Block {

    public RottingLanternBulbBlock() {
        super(
                Properties.ofFullCopy(Blocks.BASALT)
        );
    }

    protected static final VoxelShape SHAPE = Block.box(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    // not able to figure it out right now but, change this at some point in the future to be activation upon release, mob touches it and once no more touch it activates, some for the tumorous bulb

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(!level.isClientSide) {
            level.destroyBlock(pos, false);

            Vec3 vec3 = Vec3.atCenterOf(pos);

            for (int i = 0; i < 20; i++) {

                int min = -90, max = 180;
                int rotX = level.getRandom().nextIntBetweenInclusive(min, max);
                int rotY = level.getRandom().nextIntBetweenInclusive(min, max);

                LanternBulbSliceEntity projectile = new LanternBulbSliceEntity(level, vec3.x, vec3.y, vec3.z, 4);
                projectile.shootFromRotationNoShooter(rotX, rotY, 0.0F, 1.0F, 1.0F);
                level.addFreshEntity(projectile);

            }
        }
        ParticleUtils.spawnParticleInBlock(level, pos, 10, ParticleTypes.GLOW_SQUID_INK);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.destroyBlock(pos, true);
    }
}
