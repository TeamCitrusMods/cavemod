package com.elysiasilly.cavemod.datagen;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.datagen.provider.BlockStateProvider;
import com.elysiasilly.cavemod.datagen.provider.ItemModelProvider;
import com.elysiasilly.cavemod.datagen.provider.lang.EnUsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;


@EventBusSubscriber(modid = CaveMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.getGenerator().addProvider(event.includeClient(), new EnUsProvider(packOutput));
        event.getGenerator().addProvider(event.includeClient(), new BlockStateProvider(packOutput, existingFileHelper));
        event.getGenerator().addProvider(event.includeClient(), new ItemModelProvider(packOutput, existingFileHelper));
    }
}
