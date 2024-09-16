package com.elysiasilly.cavemod.world.feature;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.*;

public class RefactoredLushFossilFeature extends Feature<NoneFeatureConfiguration> {

    public RefactoredLushFossilFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos originPos = context.origin();
        WorldGenLevel level = context.level();

        BlockPos floorPos = originPos.above(5);

        // TODO : remove cyan concrete check lol
        while(level.getBlockState(floorPos).canBeReplaced() || level.getBlockState(floorPos).is(Blocks.CYAN_CONCRETE)) {
            //setBlock(level, floorPos, Blocks.DIRT.defaultBlockState());
            floorPos = floorPos.below();
        }

        floorPos = floorPos.above();

        Dictionary<BlockPos, Direction.Axis> volumeKey = new Hashtable<>();

        Set<BlockPos> volume = new HashSet<>();

        int segmentAmount = level.getRandom().nextIntBetweenInclusive(2, 3);

        int segmentSpacing = level.getRandom().nextIntBetweenInclusive(2, 3);

        int radius = level.getRandom().nextIntBetweenInclusive(3, 6);

        //radius = 3;

        int clampCorners = 3;
        int addToRadius = 0;
        int qhar = 0;
        int bruh = 0;

        switch(radius) {
            case 3 : addToRadius = 1; qhar = 0; break;
            case 4 : addToRadius = 1; qhar = 0; break;
            case 5 : bruh = 1; addToRadius = 1; qhar = 1; break;
            case 6 : bruh = 1; addToRadius = 2; qhar = 2; break;
            case 7 : bruh = 1; addToRadius = 2; qhar = 3; break;
        }

        System.out.println(radius);

        //segmentsAmount = 10;

        for(int i = -segmentAmount; i < segmentAmount; i++) {
            for(int x = -radius; x <= radius; ++x) {
                for(int y = -radius; y <= radius; ++y) {
                    if((x * x + y * y >= radius + addToRadius * radius + addToRadius) && (x * x + y * y <= radius + clampCorners * radius + clampCorners) && !(x * x + y * y <= (radius - 1) + (clampCorners - 1) * (radius - 1) + (clampCorners - 1))) {
                        if(!(y == -radius + qhar - 1 || y == -radius + qhar)) {

                            if (Math.abs(x) != Math.abs(y)) {
                                if (Math.abs(x) > Math.abs(y)) {
                                    volumeKey.put(floorPos.offset(x, y, i * segmentSpacing), Direction.Axis.Y);

                                    //setBlock(level, floorPos.offset(x, 0, y), CM$Blocks.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z));
                                } else {
                                    volumeKey.put(floorPos.offset(x, y, i * segmentSpacing), Direction.Axis.X);

                                    //setBlock(level, floorPos.offset(x, 0, y), CM$Blocks.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X));
                                }
                            } else {
                                volumeKey.put(floorPos.offset(x, y, i * segmentSpacing), Direction.Axis.Z);

                                //setBlock(level, floorPos.offset(x, 0, y), CM$Blocks.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
                            }


                            //volume.add(floorPos.offset(x, y, i * segmentSpacing));
                        }
                    }
                }
            }
        }

        for(int i = (-segmentAmount * segmentSpacing) - 1; i < (segmentAmount * segmentSpacing); i++) {
            volume.add(floorPos.offset(0, radius - bruh, i));



            volumeKey.put(floorPos.offset(0, radius - bruh, i), Direction.Axis.Z);
        }

        //BlockPos temporary;

        Enumeration<BlockPos> k = volumeKey.keys();



        while (k.hasMoreElements()) {

            BlockPos key = k.nextElement();

            if(level.getBlockState(key.above()).canBeReplaced()) {
                setBlock(level, key.above(), Blocks.MOSS_CARPET.defaultBlockState());
            }
            setBlock(level, key, BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.AXIS, volumeKey.get(key)));

            //System.out.println("Key: " + key + ", Value: " + volumeKey.get(key));
        }



        HashMap<BlockPos, Direction> LM = new HashMap<>((Map) volumeKey.keys());

        //LM.

        System.out.println(LM);

        List<BlockPos> lmao = new ArrayList<BlockPos>((Collection<? extends BlockPos>) volumeKey.keys());

        int volumeSize = lmao.size();

        int randomBlockInVolume = level.getRandom().nextIntBetweenInclusive(0, volumeSize);

        BlockPos randomPos = lmao.get(randomBlockInVolume);
        System.out.println(randomPos);

        setBlock(level, randomPos, Blocks.DIAMOND_BLOCK.defaultBlockState());


        return true;
    }
}
