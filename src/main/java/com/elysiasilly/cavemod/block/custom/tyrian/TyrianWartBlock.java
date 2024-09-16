package com.elysiasilly.cavemod.block.custom.tyrian;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TyrianWartBlock extends NetherWartBlock {
    public TyrianWartBlock() {
        super(Properties.ofFullCopy(Blocks.NETHER_WART));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockRegistry.TYRIAN_WART_BLOCK.get()) || state.canSustainPlant(level, pos.below(), Direction.DOWN, state).isTrue();
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }
}
