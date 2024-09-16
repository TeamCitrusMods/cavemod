package com.elysiasilly.cavemod.block.custom.shaderblock;

import com.elysiasilly.cavemod.registry.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ShaderBlockEntity extends BlockEntity {

    public ShaderBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public ShaderBlockEntity(BlockPos pos, BlockState blockState) {
        this(BlockEntityTypeRegistry.SHADER_BLOCK_BE.get(), pos, blockState);
    }

    /*
    public boolean shouldRenderFace(Direction face) {
        return face.getAxis() == Direction.Axis.Y;
    }

     */
}
