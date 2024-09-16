package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.world.feature.DebugFeature;
import com.elysiasilly.cavemod.world.feature.DripLeavesBasinFeature;
import com.elysiasilly.cavemod.world.feature.LushFossilFeature;
import com.elysiasilly.cavemod.world.feature.RefactoredLushFossilFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, CaveMod.MODID);


    public static final DeferredHolder<Feature<?>, LushFossilFeature> BONETOWN_FEATURE
            = FEATURES.register("bonetown", LushFossilFeature::new);

    public static final DeferredHolder<Feature<?>, DripLeavesBasinFeature> DRIPLEAVES_BASIN_FEATURE
            = FEATURES.register("dripleaves_basin", DripLeavesBasinFeature::new);

    public static final DeferredHolder<Feature<?>, DebugFeature> DEBUG_FEATURE
            = FEATURES.register("debug", DebugFeature::new);

    public static final DeferredHolder<Feature<?>, RefactoredLushFossilFeature> REFACTORED_BONETOWN_FEATURE
            = FEATURES.register("refactored_bonetown", RefactoredLushFossilFeature::new);
}


