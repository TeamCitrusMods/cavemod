package com.elysiasilly.cavemod.block.custom.oxylotus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class AirBudBlock extends Block {

    public AirBudBlock() {
        super(Properties.ofFullCopy(Blocks.SLIME_BLOCK));
    }


    /*
    private InteractionResult useBud(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide())
            level.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 5, 1);
        level.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.EMPTY_BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
        if (level instanceof ServerLevel server) server.sendParticles(ParticleTypes.BUBBLE,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 12, 0.5, 0.5, 0.5, 0.01);
        player.setAirSupply(0);
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,
                (player.hasEffect(MobEffects.WATER_BREATHING) ? Objects.requireNonNull(player.getEffect(MobEffects.WATER_BREATHING)).getDuration() : 0) + 200, 0));

        if (!level.isClientSide() && player.hasEffect(MobEffects.WATER_BREATHING) && Objects.requireNonNull(player.getEffect(MobEffects.WATER_BREATHING)).getDuration() > 1200) {
            level.playSound(null, pos, SoundEvents.AMBIENT_CAVE.get(), SoundSource.BLOCKS, 1, 1);
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1));
        }




        return InteractionResult.SUCCESS;
    }

     */

        /*
        @Override
        public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
            super.entityInside(state, world, pos, entity);
            if (entity instanceof LivingEntity living && state.getValue(TYPE) == Type.BUD) useBud(state, world, pos, living);
            else if (entity instanceof LivingEntity living && state.getValue(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos, living);
        }

         */

    /*
    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(state, world, pos, entity);
        if (entity instanceof LivingEntity living && state.getValue(TYPE) == Type.BUD) useBud(state, world, pos, living);
        else if (entity instanceof LivingEntity living && state.getValue(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos, living);    }
     */

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {


        if(!level.isClientSide())
            player.setAirSupply(300);


        /*
        useBud(state, level, pos, player);

         */

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
