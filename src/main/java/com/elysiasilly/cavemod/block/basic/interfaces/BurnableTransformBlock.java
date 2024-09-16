package com.elysiasilly.cavemod.block.basic.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface BurnableTransformBlock {

    Block getBlockToTransformInto(BlockState blockState);

}
