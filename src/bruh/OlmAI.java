package com.elysiasilly.cavemod.entity;

import com.elysiasilly.cavemod.registry.CM$Blocks;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Predicate;

public class OlmAI {



    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);


    protected static Brain<?> makeBrain(Brain<OlmEntity> brain) {
        initIdleActivity(brain);
        brain.setDefaultActivity(Activity.IDLE);
        return brain;
    }

    public static Predicate<ItemStack> getTemptations() {
        return (stack) -> {
            return stack.is(CM$Blocks.WHITE_FLOWER.get().asItem());
        };
    }

    public static void updateActivity(OlmEntity olm) {
        Brain<OlmEntity> brain = olm.getBrain();
       // Activity activity = (Activity)brain.getActiveNonCoreActivity().orElse((Object)null);
       // if (activity != Activity.PLAY_DEAD) {
            brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
            //if (activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse((Object)null) != Activity.FIGHT) {
           //     brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
           // }
       // }

    }

    private static void initIdleActivity(Brain<OlmEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(


                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),

                Pair.of(1, new AnimalMakeLove(EntityType.AXOLOTL, 0.2F, 2)),

                Pair.of(2, new RunOne(ImmutableList.of(Pair.of(new FollowTemptation(OlmAI::getSpeedModifier), 1),
                        Pair.of(BabyFollowAdult.create(ADULT_FOLLOW_RANGE, OlmAI::getSpeedModifierFollowingAdult), 1)))),

                //Pair.of(3, StartAttacking.create(AxolotlAi::findNearestValidAttackTarget)),

                Pair.of(3, TryFindWater.create(6, 0.15F)),

                Pair.of(4, new GateBehavior(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(RandomStroll.swim(0.5F), 2), Pair.of(RandomStroll.stroll(0.15F, false), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(OlmAI::canSetWalkTargetFromLookTarget, OlmAI::getSpeedModifier, 3), 3),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5))))));
    }

    private static float getSpeedModifier(LivingEntity entity) {
        return entity.isInWaterOrBubble() ? 0.5F : 0.15F;
    }

    private static float getSpeedModifierFollowingAdult(LivingEntity entity) {
        return entity.isInWaterOrBubble() ? 0.6F : 0.15F;
    }

    private static boolean canSetWalkTargetFromLookTarget(LivingEntity entity) {
        Level level = entity.level();
        Optional<PositionTracker> optional = entity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
        if (optional.isPresent()) {
            BlockPos blockpos = ((PositionTracker)optional.get()).currentBlockPosition();
            return level.isWaterAt(blockpos) == entity.isInWaterOrBubble();
        } else {
            return false;
        }
    }
}
