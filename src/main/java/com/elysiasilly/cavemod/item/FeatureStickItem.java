package com.elysiasilly.cavemod.item;

import com.elysiasilly.cavemod.registry.resourcekeys.CM$Features;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Objects;

public class FeatureStickItem extends Item {
    public FeatureStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        //ServerLevel level = Objects.requireNonNull(context.getLevel()).getServer();
        BlockPos clickedBlockPos = context.getClickedPos(); //.relative(context.getClickedFace());
        Block clickedBlock = level.getBlockState(clickedBlockPos).getBlock();

        if (level instanceof ServerLevel serverLevel) {
            System.out.println("bruh");

            if(clickedBlock == Blocks.WHITE_CONCRETE) {
                awooga(serverLevel, clickedBlockPos ,CM$Features.BONETOWN);
            }
            if(clickedBlock == Blocks.LIME_CONCRETE) {
                awooga(serverLevel, clickedBlockPos ,CM$Features.DRIPLEAVES_BASIN);
            }
            if(clickedBlock == Blocks.YELLOW_CONCRETE) {
                awooga(serverLevel, clickedBlockPos ,CM$Features.DEBUG);
            }
            if(clickedBlock == Blocks.CYAN_CONCRETE) {
                awooga(serverLevel, clickedBlockPos ,CM$Features.REFACTORED_BONETOWN);
            }
        }

        return InteractionResult.SUCCESS;

    }

    public void awooga(ServerLevel level, BlockPos clickedBlockPos, ResourceKey<ConfiguredFeature<?, ?>> featureResourceKey) {

        RandomSource random = level.getRandom();

        level.destroyBlock(clickedBlockPos, false);

        level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
                .flatMap(param1 -> param1.getHolder(featureResourceKey))
                .ifPresent(param2 -> param2.value().place(level, level.getChunkSource().getGenerator(), random, clickedBlockPos));


    }
}
