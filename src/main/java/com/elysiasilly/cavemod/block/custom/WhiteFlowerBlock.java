package com.elysiasilly.cavemod.block.custom;

import com.elysiasilly.cavemod.registry.ParticleTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class WhiteFlowerBlock extends TallFlowerBlock {
    public WhiteFlowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
            //super.animateTick(state, level, pos, random);

            ParticleUtils.spawnParticles(level, pos.above(), 2, 0.05 , 0.05, true, ParticleTypeRegistry.WHITE_FLOWER_PARTICLE.get());
        }
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        super.spawnDestroyParticles(level, player, pos, state);
    }
}
