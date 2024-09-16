package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.entity.OlmEntity;
import com.elysiasilly.cavemod.item.lanternbulbslice.LanternBulbSliceEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = CaveMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, CaveMod.MODID);

    public static final Supplier<EntityType<LanternBulbSliceEntity>> LANTERN_BULB_SLICE = ENTITIES.register("lantern_bulb_slice", () -> (
            EntityType.Builder.<LanternBulbSliceEntity>of(LanternBulbSliceEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("lantern_bulb_slice")));

    public static final Supplier<EntityType<OlmEntity>> OLM_ENTITY = ENTITIES.register("olm", () -> (
            EntityType.Builder.of(OlmEntity::new, MobCategory.AXOLOTLS)
                    .sized(0.5f, 0.5f)
                    .build("olm")));

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(OLM_ENTITY.get(), OlmEntity.createAttributes().build());
    }
}
