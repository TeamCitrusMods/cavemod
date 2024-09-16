package com.elysiasilly.cavemod.block.basic;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class RepeatingPatternBlock extends Block {

    public static final IntegerProperty PATTERN = IntegerProperty.create("pattern", 0, 9);

    public RepeatingPatternBlock(Properties properties, int patternLenght) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PATTERN,1)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction clickedFace = context.getClickedFace();

        int pattern = context.getClickedPos().getY() % 3;


        int combinedXZ = (context.getClickedPos().getX() + context.getClickedPos().getZ());
        int pattern2 = (combinedXZ / 2) % 3;

        int patternTest = context.getClickedPos().getX() % 3;



        int adjustedPattern = (pattern + 1) * (pattern2 + 1);


        int Z = (context.getClickedPos().getZ() % 3) + 1;
        int Y = (context.getClickedPos().getX() % 3) + 1;

        int combinedZX = Z * Y;

        return super.getStateForPlacement(context).setValue(PATTERN, combinedZX);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PATTERN);
    }
}
