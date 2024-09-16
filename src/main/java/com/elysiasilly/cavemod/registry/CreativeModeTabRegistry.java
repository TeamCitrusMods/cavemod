package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeModeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVETABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CaveMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CAVEMOD = CREATIVETABS.register("cavemod", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.cavemod"))
            .icon(() -> new ItemStack(BlockRegistry.VELVET_MOSS_BLOCK.get()))
            .displayItems((parameters, output) -> {
                // gets all items in BlockRegistry
                for (DeferredHolder<Item, ? extends Item> item : BlockRegistry.BLOCKITEMS.getEntries()) {
                    output.accept(item.get());
                }
                for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
                    output.accept(item.get());
                }
                /*
                // gets all items in ItemRegistry
                for (DeferredHolder<Item, ? extends Item> item : MUItem.ITEMS.getEntries()) {
                    output.accept(item.get());
                }

                 */
            })
            .build()
    );
}
