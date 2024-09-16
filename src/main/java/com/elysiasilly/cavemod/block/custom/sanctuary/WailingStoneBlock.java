package com.elysiasilly.cavemod.block.custom.sanctuary;

import com.elysiasilly.cavemod.utils.RandomNumber;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class WailingStoneBlock extends Block {
    public WailingStoneBlock() {
        super(Properties.ofFullCopy(Blocks.TUFF));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 2.0F, 0.5F + level.random.nextFloat() * 1.2F);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 2.0F, 0.5F + level.random.nextFloat() * 1.2F);
            level.playSound(null, pos, SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM, SoundSource.BLOCKS, 2.0F, 0.5F + level.random.nextFloat() * 1.2F);

            int i = RandomNumber.between(0, 24);
            //System.out.println(i);

            level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));

            //level.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);

            //ParticleUtils.spawnParticleInBlock(level, pos.above(), 1, ParticleTypes.NOTE);

            //level.destroyBlock(pos, false);
            //level.setBlock(pos, CM$Blocks.WAILING_STONE.get().defaultBlockState(), 3);


            //level.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, i, 0.0, 0.0);

        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
