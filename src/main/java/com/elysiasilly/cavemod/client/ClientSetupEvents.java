package com.elysiasilly.cavemod.client;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.block.custom.shaderblock.ShaderBlockRenderer;
import com.elysiasilly.cavemod.entity.OlmRenderer;
import com.elysiasilly.cavemod.particle.SanctuaryMossParticle;
import com.elysiasilly.cavemod.particle.SweetwoodLeafParticle;
import com.elysiasilly.cavemod.particle.WhiteFlowerParticle;
import com.elysiasilly.cavemod.registry.BlockEntityTypeRegistry;
import com.elysiasilly.cavemod.registry.EntityTypeRegistry;
import com.elysiasilly.cavemod.registry.ParticleTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = CaveMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientSetupEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.LANTERN_BULB_SLICE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.OLM_ENTITY.get(), OlmRenderer::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleTypeRegistry.SANCTUARY_MOSS_PARTICLE.get(), SanctuaryMossParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypeRegistry.WHITE_FLOWER_PARTICLE.get(), WhiteFlowerParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypeRegistry.SWEETWOOD_LEAF_PARTICLE.get(), SweetwoodLeafParticle.Factory::new);
    }


}
