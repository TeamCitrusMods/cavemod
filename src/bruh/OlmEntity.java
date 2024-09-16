package com.elysiasilly.cavemod.entity;

import com.elysiasilly.cavemod.registry.CM$Blocks;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class OlmEntity extends Animal{

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super OlmEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, SensorType.AXOLOTL_ATTACKABLES, SensorType.AXOLOTL_TEMPTATIONS
    );

    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.PLAY_DEAD_TICKS,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.HAS_HUNTING_COOLDOWN,
            MemoryModuleType.IS_PANICKING
    );

    public OlmEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new OlmEntity.OlmMoveControl(this);
        //this.lookControl = new Axolotl.AxolotlLookControl(this, 20);
    }

    static class OlmMoveControl extends SmoothSwimmingMoveControl {

        private final OlmEntity olmEntity;

        public OlmMoveControl(OlmEntity olmEntity) {
            super(olmEntity, 85, 10, 0.1F, 0.5F, false);
            this.olmEntity = olmEntity;
        }

        @Override
        public void tick() {
            //if (!this.axolotl.isPlayingDead()) {
                super.tick();
            //}
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(CM$Blocks.WHITE_FLOWER.get().asItem());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return OlmAI.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<OlmEntity> getBrain() {
        return (Brain<OlmEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {

        this.level().getProfiler().push("olmBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("olmActivityUpdate");
        OlmAI.updateActivity(this);
        this.level().getProfiler().pop();

        //super.customServerAiStep();
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0.0F;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    protected Brain.Provider<OlmEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    /*
    public static enum Variant implements StringRepresentable {
        ELYSIA(0, "elysia"),
        CLOVER(1, "clover");

        private static final IntFunction<OlmEntity.Variant> BY_ID = ByIdMap.continuous(OlmEntity.Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Axolotl.Variant> CODEC = StringRepresentable.fromEnum(Axolotl.Variant::values);
        private final int id;
        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name  = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public static OlmEntity.Variant byId(int id) {
            return (OlmEntity.Variant)BY_ID.apply(id);
        }

        @Override
        public String getSerializedName() {return this.name;}
    }

     */

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }
}
