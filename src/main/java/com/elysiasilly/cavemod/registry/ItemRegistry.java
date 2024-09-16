package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.item.FeatureStickItem;
import com.elysiasilly.cavemod.item.SweetwoodBarkItem;
import com.elysiasilly.cavemod.item.lanternbulbslice.LanternBulbSliceItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CaveMod.MODID);


    /*
    public static final DeferredItem<GPSItem> GPS_ITEM =
            ITEMS.register("gps", () ->
                    new GPSItem(new Item.Properties()
                            .stacksTo(1)
                            .component(MUComponent.BLOCK_POS, GPSComponent.EMPTY))
            );
    */

    public static final DeferredItem<Item> LANTERN_BULB_SLICE = ITEMS.register("lantern_bulb_slice", () ->
                    new LanternBulbSliceItem(new Item.Properties().stacksTo(64))
    );

    public static final DeferredItem<Item> SWEETWOOD_BARK = ITEMS.register("sweetwood_bark", () ->
            new SweetwoodBarkItem(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(0)
                            .saturationModifier(0)
                            .alwaysEdible()
                            .build()
                    )
            )
    );

    public static final DeferredItem<Item> TOOTH = ITEMS.register("tooth", () ->
            new Item(new Item.Properties().stacksTo(64))
    );

    public static final DeferredItem<Item> SWEET_GUM = ITEMS.register("sweet_gum", () ->
            new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationModifier(1)
                            .build()
                    )
            )
    );

    public static final DeferredItem<Item> FEATURE_STICK = ITEMS.register("feature_stick", () ->
            new FeatureStickItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );
}
