package com.elysiasilly.cavemod.block.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.neoforged.neoforge.common.util.TriState;

public class BasicMossBlock extends MossBlock {

    ResourceKey<ConfiguredFeature<?, ?>> MOSS_FEATURE;

    public BasicMossBlock(Properties properties, ResourceKey<ConfiguredFeature<?, ?>> mossFeature) {
        super(properties);
        MOSS_FEATURE = mossFeature;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.TRUE;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {

        // CaveFeatures.MOSS_PATCH_BONEMEAL

        pLevel.registryAccess()
                .registry(Registries.CONFIGURED_FEATURE)
                .flatMap(param1 -> param1.getHolder(MOSS_FEATURE))
                .ifPresent(param2 -> param2.value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos.above()));
    }

}
