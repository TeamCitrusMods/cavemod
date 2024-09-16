package com.elysiasilly.cavemod.mixin;

import com.elysiasilly.cavemod.block.basic.interfaces.BurnableTransformBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO : fix

@Mixin(FireBlock.class)
public class FireBlockMixin {

    @Inject(
            method = "checkBurnOut",
            at = @At(
                    value = "INVOKE",
                    //target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z",
                    target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z",
                    shift = At.Shift.AFTER
            )
    )

    private void CaveMod$checkBurnOut(Level level, BlockPos pos, int chance, RandomSource random, int age, Direction face, CallbackInfo ci, @Local BlockState before) {

        System.out.println(before);

        if(before instanceof BurnableTransformBlock) {
            //BlockState blockState = level.getBlockState(pos);

            //System.out.println(blockState);

            level.setBlockAndUpdate(pos, ((BurnableTransformBlock)before).getBlockToTransformInto(before).defaultBlockState());
        }
    }

}