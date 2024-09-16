package com.elysiasilly.cavemod.entity;

import com.elysiasilly.cavemod.CaveMod;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class OlmRenderer extends MobRenderer<OlmEntity, OlmModel<OlmEntity>> {

    private static final ResourceLocation OLM = ResourceLocation.fromNamespaceAndPath(CaveMod.MODID, "textures/entity/olm.png");

    public OlmRenderer(EntityRendererProvider.Context context) {
        super(context, new OlmModel<>(context.bakeLayer(OlmModel.LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(OlmEntity olmEntity) {
        return OLM;
    }
}
