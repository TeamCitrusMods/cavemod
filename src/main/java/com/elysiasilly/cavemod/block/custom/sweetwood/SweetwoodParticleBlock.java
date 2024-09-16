package com.elysiasilly.cavemod.block.custom.sweetwood;

import com.elysiasilly.cavemod.particle.SweetwoodLeafParticle;
import com.elysiasilly.cavemod.registry.ParticleTypeRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SweetwoodParticleBlock extends Block {
    public SweetwoodParticleBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_WOOD));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {

        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        for (int i = 0; i < 20; i++) {
            ParticleUtils.spawnParticles(level, pos, 1, 2, 2, true, ParticleTypeRegistry.SWEETWOOD_LEAF_PARTICLE.get());
        }

        return InteractionResult.SUCCESS;
    }
}
