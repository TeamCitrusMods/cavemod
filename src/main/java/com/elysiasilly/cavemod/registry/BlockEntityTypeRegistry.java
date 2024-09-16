package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.block.custom.shaderblock.ShaderBlockEntity;
import com.elysiasilly.cavemod.block.custom.stonechest.StoneChestBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityTypeRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CaveMod.MODID);

    public static final Supplier<BlockEntityType<StoneChestBlockEntity>> STONE_CHEST_BE = BLOCKENTITES.register(
            "stone_chest_be", () -> BlockEntityType.Builder.of(StoneChestBlockEntity::new, BlockRegistry.STONE_CHEST.get()).build(null)
    );

    public static final Supplier<BlockEntityType<ShaderBlockEntity>> SHADER_BLOCK_BE = BLOCKENTITES.register(
            "shader_block_be", () -> BlockEntityType.Builder.of(ShaderBlockEntity::new, BlockRegistry.STONE_CHEST.get()).build(null)
    );
}
