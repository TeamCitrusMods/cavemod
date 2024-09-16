package com.elysiasilly.cavemod.world.feature;

import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class LushFossilFeature extends Feature<NoneFeatureConfiguration> {


    public LushFossilFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private static final BlockStateProvider STRUCTURAL_BLOCKS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.COAL_ORE.defaultBlockState(), 10)
            .add(Blocks.AIR.defaultBlockState(), 10)
            .add(BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState(), 80)
            .add(Blocks.DIAMOND_ORE.defaultBlockState(), 1)
            .add(Blocks.MOSS_BLOCK.defaultBlockState(), 15)
    );

    private static final BlockStateProvider MOSS = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.MOSS_CARPET.defaultBlockState(), 80)
            .add(Blocks.AIR.defaultBlockState(), 15)
    );

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        setBlock(level, origin, Blocks.MAGENTA_CONCRETE.defaultBlockState());

        placeSegment(level, origin, random);

        return true;
    }

    private void placeSegment(WorldGenLevel level, BlockPos origin, RandomSource random) {

        int segmentsAmount = random.nextIntBetweenInclusive(2, 4);
        int segmentLenght = random.nextIntBetweenInclusive(2, 4);

        //System.out.println(segmentsAmount);

        int originX = origin.getX();
        int originY = origin.getY();
        int originZ = origin.getZ();

        int step = 1;
        //int step2 = 1;

        int segmentAmountFix = segmentsAmount + 1;

        for (int i = 1; i < segmentAmountFix; i++) {

            BlockPos.betweenClosedStream(origin.offset(step, 0, segmentLenght), origin.offset(step, 0, -segmentLenght)).forEach(pos -> {
                BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, pos);
                BlockState mossBlock = MOSS.getState(random, pos);

                if(structuralBlock.is(BlockRegistry.FOSSIL_BLOCK.get())) {
                    //System.out.println(true);
                    setBlock(level, pos, BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
                    //setBlock(level, pos.above(), Blocks.YELLOW_CONCRETE.defaultBlockState());

                } else {
                    //System.out.println(false);
                    setBlock(level, pos, structuralBlock);
                }
                if(!structuralBlock.is(Blocks.AIR)) {
                    setBlock(level, pos.above(), mossBlock);
                }
                if (structuralBlock.is(Blocks.MOSS_BLOCK)) {
                    setBlock(level, pos.above(), BlockRegistry.SHORT_MOSSPROUT.get().defaultBlockState());
                }
            });

            BlockPos.betweenClosedStream(origin.offset(-step, 0, segmentLenght), origin.offset(-step, 0, -segmentLenght)).forEach(pos -> {
                BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, pos);
                BlockState mossBlock = MOSS.getState(random, pos);
                if(structuralBlock.is(BlockRegistry.FOSSIL_BLOCK.get())) {
                    //System.out.println(true);
                    setBlock(level, pos, BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
                    //setBlock(level, pos.above(), Blocks.YELLOW_CONCRETE.defaultBlockState());

                } else {
                    //System.out.println(false);
                    setBlock(level, pos, structuralBlock);
                }
                if(!structuralBlock.is(Blocks.AIR)) {
                    setBlock(level, pos.above(), mossBlock);
                }
                if(structuralBlock.is(Blocks.MOSS_BLOCK)) {
                    setBlock(level, pos.above(), BlockRegistry.SHORT_MOSSPROUT.get().defaultBlockState());
                }
            });

            //LEGS

            BlockPos newPos = origin.offset(step, -1, segmentLenght + 1);

            while(level.getBlockState(newPos).canBeReplaced()) {
                BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, newPos);
                BlockState mossBlock = MOSS.getState(random, newPos);
                setBlock(level, newPos, structuralBlock);
                if(level.isEmptyBlock(newPos.above()) && !structuralBlock.is(Blocks.AIR)) {
                    setBlock(level, newPos.above(), mossBlock);
                }

                newPos = newPos.below();
            }

            BlockPos hi1 = origin.offset(step, -1, -segmentLenght - 1);

            while(level.getBlockState(hi1).canBeReplaced()) {
                BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, hi1);
                BlockState mossBlock = MOSS.getState(random, hi1);
                setBlock(level, hi1, structuralBlock);
                if(level.isEmptyBlock(hi1.above()) && !structuralBlock.is(Blocks.AIR)) {
                    setBlock(level, hi1.above(), mossBlock);
                }

                hi1 = hi1.below();
            }

            BlockPos hi2 = origin.offset(-step, -1, segmentLenght + 1);

            while(level.getBlockState(hi2).canBeReplaced()) {
                BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, hi2);
                BlockState mossBlock = MOSS.getState(random, hi2);
                setBlock(level, hi2, structuralBlock);
                if(level.isEmptyBlock(hi2.above()) && !structuralBlock.is(Blocks.AIR)) {
                    setBlock(level, hi2.above(), mossBlock);
                }

                hi2 = hi2.below();
            }

            BlockPos hi3 = origin.offset(-step, -1, -segmentLenght - 1);

            while(level.getBlockState(hi3).canBeReplaced()) {
                BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, hi3);
                BlockState mossBlock = MOSS.getState(random, hi3);
                setBlock(level, hi3, structuralBlock);
                if(level.isEmptyBlock(hi3.above()) && !structuralBlock.is(Blocks.AIR)) {
                    setBlock(level, hi3.above(), mossBlock);
                }

                hi3 = hi3.below();
            }

            step = step + 2;

        }

        BlockPos.betweenClosedStream(origin.offset(segmentsAmount * 2, 0, 0), origin.offset(segmentsAmount * -2, 0, 0)).forEach(pos -> {
            BlockState structuralBlock = STRUCTURAL_BLOCKS.getState(random, pos);
            BlockState mossBlock = MOSS.getState(random, pos);
            if(structuralBlock.is(BlockRegistry.FOSSIL_BLOCK.get())) {
                //System.out.println(true);
                setBlock(level, pos, BlockRegistry.FOSSIL_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                //setBlock(level, pos.above(), Blocks.YELLOW_CONCRETE.defaultBlockState());

            } else {
                //System.out.println(false);
                setBlock(level, pos, structuralBlock);
            }
            if(!structuralBlock.is(Blocks.AIR)) {
                setBlock(level, pos.above(), mossBlock);
            }
            if(structuralBlock.is(Blocks.MOSS_BLOCK)) {
                setBlock(level, pos.above(), BlockRegistry.SHORT_MOSSPROUT.get().defaultBlockState());
            }
        });
    }
}
