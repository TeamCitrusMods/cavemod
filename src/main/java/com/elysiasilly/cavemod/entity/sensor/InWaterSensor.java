package com.elysiasilly.cavemod.entity.sensor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;

import java.util.List;

public class InWaterSensor<E extends LivingEntity> extends PredicateSensor<E, E> {
    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of();
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return null;
    }
}
