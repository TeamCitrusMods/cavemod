package com.elysiasilly.cavemod.entity.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.EscapeSun;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class GetToWater<E extends PathfinderMob> extends ExtendedBehaviour<E> {

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    protected float speedModifier = 1;

    protected Vec3 hidePos = null;

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            //Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED)
            //Pair.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT)
    );

    public GetToWater() {
        noTimeout();
    }

    public GetToWater<E> speedModifier(float speedMod) {
        this.speedModifier = speedMod;

        return this;
    }

    @Override
    protected void stop(E entity) {
        this.hidePos = null;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {

        if(entity.isInWater())
            return false;

        this.hidePos = getHidePos(entity);

        return this.hidePos != null;
    }

    //

    @Override
    protected void start(E entity) {
        BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(this.hidePos, this.speedModifier, 0));
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        if (this.hidePos == null)
            return false;

        WalkTarget walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);

        if (walkTarget == null)
            return false;

        return walkTarget.getTarget().currentBlockPosition().equals(BlockPos.containing(this.hidePos)) && !entity.getNavigation().isDone();
    }

    @Nullable
    protected Vec3 getHidePos(E entity) {
        RandomSource randomsource = entity.getRandom();
        BlockPos entityPos = entity.blockPosition();

        for(int i = 0; i < 10; ++i) {
            BlockPos hidePos = entityPos.offset(randomsource.nextInt(20) - 10, randomsource.nextInt(6) - 3, randomsource.nextInt(20) - 10);

            if (!entity.level().getBlockState(hidePos).is(Blocks.WATER) && entity.getWalkTargetValue(hidePos) < 0.0F)
                return Vec3.atBottomCenterOf(hidePos);
        }

        return null;
    }
}
