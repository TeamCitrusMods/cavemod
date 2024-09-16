package com.elysiasilly.cavemod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TestingBlock extends Block {

    public TestingBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {

        spawnInk(pPos, pLevel);

        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    private void spawnInk(BlockPos pos, Level level) {

        // Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());

        for (int i = 0; i < 30; i++) {
            // Vec3 vec31 = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
            // Vec3 vec32 = vec31.scale(0.3 + (double)(this.random.nextFloat() * 2.0F));



            //((ServerLevel) this.level()).sendParticles(this.getInkParticle(), pos.getX(), pos.getY() + 2, pos.getZ(), 0, pos.getX(), pos.getY() + 1, pos.getZ(), 0.1F);
        }
    }



    protected ParticleOptions getInkParticle() {
        return ParticleTypes.SQUID_INK;
    }
}
