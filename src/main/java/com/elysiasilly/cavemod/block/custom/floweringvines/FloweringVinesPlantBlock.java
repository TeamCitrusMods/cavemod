package com.elysiasilly.cavemod.block.custom.floweringvines;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FloweringVinesPlantBlock extends GrowingPlantBodyBlock {

    public static final MapCodec<WeepingVinesPlantBlock> CODEC = simpleCodec(WeepingVinesPlantBlock::new);
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    @Override
    public MapCodec<WeepingVinesPlantBlock> codec() {
        return CODEC;
    }

    public FloweringVinesPlantBlock() {
        super(Properties.ofFullCopy(Blocks.VINE), Direction.DOWN, SHAPE, false);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockRegistry.FLOWERING_VINES_HEAD.get();
    }
}
