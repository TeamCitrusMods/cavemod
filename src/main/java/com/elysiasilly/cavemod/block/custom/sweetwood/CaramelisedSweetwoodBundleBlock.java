package com.elysiasilly.cavemod.block.custom.sweetwood;

import com.elysiasilly.cavemod.block.basic.wood.WoodenLogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CaramelisedSweetwoodBundleBlock extends WoodenLogBlock {

    public CaramelisedSweetwoodBundleBlock(Block stripResult) {
        super(stripResult);
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return super.isStickyBlock(state);
    }
}
