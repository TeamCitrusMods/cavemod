package com.elysiasilly.cavemod.block.basic;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;

public class BasicMultiFaceBlock extends MultifaceBlock {

    public static final MapCodec<BasicMultiFaceBlock> CODEC = simpleCodec(BasicMultiFaceBlock::new);
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public BasicMultiFaceBlock(Properties properties) {
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
}
