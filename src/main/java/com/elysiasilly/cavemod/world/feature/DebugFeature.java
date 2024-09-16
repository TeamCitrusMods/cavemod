package com.elysiasilly.cavemod.world.feature;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.HashSet;
import java.util.Set;

public class DebugFeature extends Feature<NoneFeatureConfiguration> {
    public DebugFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos originPos = context.origin();
        WorldGenLevel level = context.level();

        Set<BlockPos> volume = new HashSet<>();

        int radius = 5;
        int clampCorners = radius + 1;

        // radius = 3
        // radius + radius = 6
        // if closer to above 3, remove

        BlockPos.MutableBlockPos mutableBlockPos = originPos.mutable();

        for(int x = -radius; x <= radius; ++x) {
            for(int z = -radius; z <= radius; ++z) {
                //for(int y = -radius; y <= radius; ++y) {


                if(x * x + z * z <= clampCorners * clampCorners) {

                    if (Math.abs(x) != Math.abs(z)) {
                        if (Math.abs(x) > Math.abs(z)) {
                            setBlock(level, originPos.offset(x, 0, z), BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z));
                        } else {
                            setBlock(level, originPos.offset(x, 0, z), BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X));
                        }
                    } else {
                        setBlock(level, originPos.offset(x, 0, z), BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
                    }
                }
            }
        }

        return true;
    }
}
