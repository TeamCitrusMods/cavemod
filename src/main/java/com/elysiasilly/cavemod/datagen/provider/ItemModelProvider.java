package com.elysiasilly.cavemod.datagen.provider;

import com.elysiasilly.cavemod.CaveMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CaveMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //basicItem(MUItem.GPS_ITEM.get());
    }
}
