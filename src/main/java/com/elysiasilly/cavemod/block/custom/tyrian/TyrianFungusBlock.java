package com.elysiasilly.cavemod.block.custom.tyrian;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class TyrianFungusBlock extends FungusBlock {
    public TyrianFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Block requiredBlock) {
        super(feature, requiredBlock, Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.MYCELIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, level, pos);
    }
}
