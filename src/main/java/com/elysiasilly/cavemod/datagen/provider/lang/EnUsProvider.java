package com.elysiasilly.cavemod.datagen.provider.lang;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.registry.BlockRegistry;
import com.elysiasilly.cavemod.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, CaveMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {


        addItem(ItemRegistry.LANTERN_BULB_SLICE, "Lantern Bulb Slice");

        addBlock(BlockRegistry.VELVET_MOSS_BLOCK, "Velvet Moss");
        addBlock(BlockRegistry.VELVET_MOSS_CARPET, "Velvet Moss Carpet");

        addBlock(BlockRegistry.AZALEA_LOG, "Azalea Log");



        add("itemGroup.cavemod", "Cavemod");


    }
}