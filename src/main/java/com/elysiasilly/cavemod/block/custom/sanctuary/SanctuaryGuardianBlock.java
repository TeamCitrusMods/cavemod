package com.elysiasilly.cavemod.block.custom.sanctuary;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SanctuaryGuardianBlock extends Block {
    public SanctuaryGuardianBlock() {
        super(Properties.ofFullCopy(Blocks.TUFF));
    }

    // Speak no evil


    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {


        if(!level.isClientSide()) {
            Player nearestPlayer = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 100, false);
            if(nearestPlayer != null) {

                BlockPos playerBlockPos = BlockPos.containing(nearestPlayer.getPosition(1));
                BlockPos newPos = calculateNewPos(pos, playerBlockPos, level);

                level.destroyBlock(pos, false);
                level.setBlockAndUpdate(newPos, this.defaultBlockState());
            }
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public int move(int currentPos, int destinyPos) {
        if(currentPos == destinyPos) {
            return 0;
        } else {
            if(currentPos > destinyPos) {
                return -1;
            } {
                return 1;
            }
        }
    }

    public BlockPos calculateNewPos(BlockPos currentPos, BlockPos playerBlockPos, Level level) {

        int currentX = currentPos.getX();
        int currentY = currentPos.getY();
        int currentZ = currentPos.getZ();

        int moveX = move(currentX, playerBlockPos.getX());
        int moveY = move(currentY, playerBlockPos.getY());
        int moveZ = move(currentZ, playerBlockPos.getZ());

        BlockPos newPos = BlockPos.containing(currentX + moveX, currentY + moveY, currentZ + moveZ);

        return level.getBlockState(newPos).canBeReplaced() ? newPos : currentPos;
    }
}
