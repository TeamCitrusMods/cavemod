package com.elysiasilly.cavemod.entity;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LayerDefinitions {
    public static Map<ModelLayerLocation, LayerDefinition> createRoots() {
        ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();


        LayerDefinition layerdefinition8 = OlmModel.createBodyLayer();


        LayerDefinition layerdefinition23 = SignRenderer.createSignLayer();
        WoodType.values().forEach(p_171114_ -> builder.put(ModelLayers.createSignModelName(p_171114_), layerdefinition23));
        LayerDefinition layerdefinition24 = HangingSignRenderer.createHangingSignLayer();
        WoodType.values().forEach(p_247864_ -> builder.put(ModelLayers.createHangingSignModelName(p_247864_), layerdefinition24));
        net.neoforged.neoforge.client.ClientHooks.loadLayerDefinitions(builder);
        ImmutableMap<ModelLayerLocation, LayerDefinition> immutablemap = builder.build();
        List<ModelLayerLocation> list = ModelLayers.getKnownLocations().filter(p_171117_ -> !immutablemap.containsKey(p_171117_)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            throw new IllegalStateException("Missing layer definitions: " + list);
        } else {
            return immutablemap;
        }
    }

}
