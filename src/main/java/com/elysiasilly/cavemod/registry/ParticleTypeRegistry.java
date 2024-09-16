package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, CaveMod.MODID);

    public static final Supplier<SimpleParticleType> SANCTUARY_MOSS_PARTICLE = PARTICLES.register("sanctuary_moss_particle",
            () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> WHITE_FLOWER_PARTICLE = PARTICLES.register("white_flower_particle",
            () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SWEETWOOD_LEAF_PARTICLE = PARTICLES.register("sweetwood_leaf_particle",
            () -> new SimpleParticleType(true));
}
