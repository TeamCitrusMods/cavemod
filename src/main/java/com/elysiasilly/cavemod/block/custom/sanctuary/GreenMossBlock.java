package com.elysiasilly.cavemod.block.custom.sanctuary;

import com.elysiasilly.cavemod.block.basic.BonemealableMultiFaceBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class GreenMossBlock extends MultifaceBlock implements BonemealableBlock {

    public final MultifaceSpreader spreader = new MultifaceSpreader(this);
    public static final MapCodec<GreenMossBlock> CODEC = simpleCodec(GreenMossBlock::new);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);

    public GreenMossBlock(Properties properties) {
        super(properties);
        //registerDefaultState(getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        for (Direction direction : DIRECTIONS) {
            if (this.isFaceSupported(direction)) {
                builder.add(getFaceProperty(direction));
            }
        }

        builder.add(AGE);

    }



    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState state) {

        if (state.getValue(AGE) == 9) {
            return Direction.stream().anyMatch((match) ->
                    this.spreader.canSpreadInAnyDirection(state, levelReader, blockPos, match.getOpposite()));
        }


        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {

        spreader.spreadFromRandomFaceTowardRandomDirection(state, level, pos, random);


        if( state.getValue(AGE) != 2 ) {
            level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 3);

        }



    }
}
