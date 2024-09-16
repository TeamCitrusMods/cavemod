package com.elysiasilly.cavemod;

import com.elysiasilly.cavemod.block.custom.shaderblock.ShaderBlockRenderer;
import com.elysiasilly.cavemod.entity.OlmModel;
import com.elysiasilly.cavemod.registry.*;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CaveMod.MODID)
public class CaveMod
{
    public static final String MODID = "cavemod";


    public CaveMod(IEventBus bus, ModContainer modContainer)
    {
        BlockRegistry.BLOCKS.register(bus);
        BlockRegistry.BLOCKITEMS.register(bus);
        CreativeModeTabRegistry.CREATIVETABS.register(bus);
        BlockEntityTypeRegistry.BLOCKENTITES.register(bus);
        ItemRegistry.ITEMS.register(bus);
        EntityTypeRegistry.ENTITIES.register(bus);
        FeatureRegistry.FEATURES.register(bus);
        ParticleTypeRegistry.PARTICLES.register(bus);

        //DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(this::registerLayerDefinitions);
        //});




        //
        //bus.addListener(this::registerCapabilities);
        bus.addListener(this::registerRenderers);
        bus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }
    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OlmModel.LOCATION, OlmModel::createBodyLayer);
    }

    @SubscribeEvent
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityTypeRegistry.SHADER_BLOCK_BE.get(), ShaderBlockRenderer::new);
    }
}
