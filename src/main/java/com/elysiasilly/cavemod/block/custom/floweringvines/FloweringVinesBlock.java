package com.elysiasilly.cavemod.block.custom.floweringvines;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FloweringVinesBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<WeepingVinesBlock> CODEC = simpleCodec(WeepingVinesBlock::new);
    protected static final VoxelShape SHAPE = Block.box(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    @Override
    public MapCodec<WeepingVinesBlock> codec() {
        return CODEC;
    }

    public FloweringVinesBlock() {
        super(Properties.ofFullCopy(Blocks.VINE), Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource p_222680_) {
        return NetherVines.getBlocksToGrowWhenBonemealed(p_222680_);
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.FLOWERING_VINES_BODY.get();
    }

    @Override
    protected boolean canGrowInto(BlockState p_154971_) {
        return NetherVines.isValidGrowthState(p_154971_);
    }
}
