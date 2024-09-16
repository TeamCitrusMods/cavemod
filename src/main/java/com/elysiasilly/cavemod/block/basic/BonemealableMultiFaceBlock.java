package com.elysiasilly.cavemod.block.basic;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;

public class BonemealableMultiFaceBlock extends MultifaceBlock implements BonemealableBlock {

    public final MultifaceSpreader spreader = new MultifaceSpreader(this);
    public static final MapCodec<BonemealableMultiFaceBlock> CODEC = simpleCodec(BonemealableMultiFaceBlock::new);
    public BonemealableMultiFaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return Direction.stream().anyMatch((match) ->
                this.spreader.canSpreadInAnyDirection(blockState, levelReader, blockPos, match.getOpposite()));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        this.spreader.spreadFromRandomFaceTowardRandomDirection(blockState, serverLevel, blockPos, randomSource);
    }
}
