package com.elysiasilly.cavemod.registry.resourcekeys;

import com.elysiasilly.cavemod.CaveMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CM$Features {
    public static ResourceKey<ConfiguredFeature<?, ?>> VELVET_MOSS_PATCH_BONEMEAL
            = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "velvet_moss_patch_bonemeal"));

    public static ResourceKey<ConfiguredFeature<?, ?>> SANCTUARY_MOSS_PATCH_BONEMEAL
            = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "sanctuary_moss_patch_bonemeal"));

    public static ResourceKey<ConfiguredFeature<?, ?>> BONETOWN
            = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "bonetown"));

    public static ResourceKey<ConfiguredFeature<?, ?>> DRIPLEAVES_BASIN
            = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "dripleaves_basin"));

    public static ResourceKey<ConfiguredFeature<?, ?>> DEBUG
            = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "debug"));

    public static ResourceKey<ConfiguredFeature<?, ?>> REFACTORED_BONETOWN
            = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "refactored_bonetown"));

}
