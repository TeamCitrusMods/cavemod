package com.elysiasilly.cavemod.registry;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.block.basic.*;
import com.elysiasilly.cavemod.block.basic.TallGrassBlock;
import com.elysiasilly.cavemod.block.basic.interfaces.BasicDirectionalBlock;
import com.elysiasilly.cavemod.block.basic.wood.*;
import com.elysiasilly.cavemod.block.custom.*;
import com.elysiasilly.cavemod.block.custom.detoriatedworkbench.DeterioratedCraftingTableBlock;
import com.elysiasilly.cavemod.block.custom.floweringvines.FloweringVinesBlock;
import com.elysiasilly.cavemod.block.custom.floweringvines.FloweringVinesPlantBlock;
import com.elysiasilly.cavemod.block.custom.lanternkelp.*;
import com.elysiasilly.cavemod.block.custom.oxylotus.AirBudBlock;
import com.elysiasilly.cavemod.block.custom.sanctuary.*;
import com.elysiasilly.cavemod.block.custom.shaderblock.ShaderBlock;
import com.elysiasilly.cavemod.block.custom.stonechest.StoneChestBlock;
import com.elysiasilly.cavemod.block.custom.sweetwood.ShortSweetrootBlock;
import com.elysiasilly.cavemod.block.custom.sweetwood.SweetwoodLogBlock;
import com.elysiasilly.cavemod.block.custom.sweetwood.SweetwoodParticleBlock;
import com.elysiasilly.cavemod.block.custom.sweetwood.TallSweetrootBlock;
import com.elysiasilly.cavemod.block.custom.tyrian.TyrianWartBlock;
import com.elysiasilly.cavemod.item.lanternbulbslice.LanternPasteBlock;
import com.elysiasilly.cavemod.registry.resourcekeys.CM$Features;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CaveMod.MODID);
    public static final DeferredRegister.Items BLOCKITEMS = DeferredRegister.createItems(CaveMod.MODID);

    public static final BlockSetType AZALEA = BlockSetType.register(new BlockSetType("azalea"));
    public static final WoodType AZALEA_WOOD_TYPE = WoodType.register(new WoodType(CaveMod.MODID + ":azalea", AZALEA));

    public static final BlockSetType SWEETWOOD = BlockSetType.register(new BlockSetType("sweetwood"));
    public static final WoodType SWEETWOOD_WOOD_TYPE = WoodType.register(new WoodType(CaveMod.MODID + ":azalea", SWEETWOOD));

    // VELVET MOSS

    public static final Supplier<? extends Block> VELVET_MOSS_BLOCK
            = registerBlockItem("velvet_moss_block", () -> new BasicMossBlock(getProperties(Blocks.MOSS_BLOCK), CM$Features.VELVET_MOSS_PATCH_BONEMEAL));

    public static final Supplier<? extends Block> VELVET_MOSS_CARPET
            = registerBlockItem("velvet_moss_carpet", () -> new CarpetBlock(getProperties(Blocks.MOSS_CARPET)));

    public static final Supplier<? extends Block> TALL_VELVET_MOSSPROUT
            = registerBlockItem("tall_velvet_mosssprout", TallGrassBlock::new);

    public static final Supplier<? extends Block> SHORT_VELVET_MOSSSPROUT
            = registerBlockItem("short_velvet_mosssprout", () -> new ShortGrassBlock(TALL_VELVET_MOSSPROUT.get()));

    // SANCTUARY MOSS

    public static final Supplier<? extends Block> SANCTUARY_MOSS_BLOCK
            = registerBlockItem("sanctuary_moss_block", () -> new BasicMossBlock(getProperties(Blocks.MOSS_BLOCK), CM$Features.SANCTUARY_MOSS_PATCH_BONEMEAL));

    public static final Supplier<? extends Block> SANCTUARY_MOSS_CARPET
            = registerBlockItem("sanctuary_moss_carpet", () -> new CarpetBlock(getProperties(Blocks.MOSS_CARPET)));

    public static final Supplier<? extends Block> TALL_SANCTUARY_MOSSPROUT
            = registerBlockItem("tall_sanctuary_mosssprout", TallSanctuaryGrassBlock::new);

    public static final Supplier<? extends Block> SHORT_SANCTUARY_MOSSSPROUT
            = registerBlockItem("short_sanctuary_mosssprout", () -> new ShortGrassBlock(TALL_SANCTUARY_MOSSPROUT.get()));

    public static final Supplier<? extends Block> LARGE_SANCTUARY_BUSH
            = registerBlockItem("large_sanctuary_bush", TallGrassBlock::new);

    public static final Supplier<? extends Block> SMALL_SANCTUARU_BUSH
            = registerBlockItem("small_sanctuary_bush", () -> new ShortGrassBlock(LARGE_SANCTUARY_BUSH.get()));

    /*
    public static final Supplier<? extends Block> SANCTUARY_VINES_HEAD
            = registerBlockItem("sanctuary_vines_head", FloweringVinesBlock::new);

    public static final Supplier<Block> SANCTUARY_VINES_BODY
            = BLOCKS.register("sanctuary_vines_body", FloweringVinesPlantBlock::new);

     */

    public static final Supplier<? extends Block> GREEN_MOSS
            = registerBlockItem("green_moss", () -> new GreenMossBlock(getProperties(Blocks.PINK_PETALS)));

    public static final Supplier<? extends Block> GREEN_MOSS_SHELF
            = registerBlockItem("green_moss_shelf", BasicDirectionalBlock::new);

    public static final Supplier<? extends Block> TYRIAN_STEM
            = registerBlockItem("tyrian_stem", () -> new RotatedPillarBlock(getProperties(Blocks.CRIMSON_STEM)));

    public static final Supplier<? extends Block> TYRIAN_WART_BLOCK
            = registerBlockItem("tyrian_wart_block", () -> new Block(getProperties(Blocks.NETHER_WART_BLOCK)));

    public static final Supplier<? extends Block> TYRIAN_WART
            = registerBlockItem("tyrian_wart", TyrianWartBlock::new);

    public static final Supplier<? extends Block> WHITE_FLOWER
            = registerBlockItem("white_flower", () -> new WhiteFlowerBlock(getProperties(Blocks.ROSE_BUSH)));



    // AZALEA WOOD

    public static final Supplier<? extends Block> STRIPPED_AZALEA_LOG
            = registerBlockItem("stripped_azalea_log", () -> new WoodenLogBlock(null));

    public static final Supplier<? extends Block> STRIPPED_AZALEA_WOOD
            = registerBlockItem("stripped_azalea_wood", () -> new WoodenLogBlock(null));

    public static final Supplier<? extends Block> AZALEA_LOG
            = registerBlockItem("azalea_log", () -> new WoodenLogBlock(STRIPPED_AZALEA_LOG.get()));

    public static final Supplier<? extends Block> AZALEA_WOOD
            = registerBlockItem("azalea_wood", () -> new WoodenLogBlock(STRIPPED_AZALEA_WOOD.get()));

    public static final Supplier<? extends Block> AZALEA_PLANKS
            = registerBlockItem("azalea_planks", WoodenPlanksBlock::new);

    public static final Supplier<? extends Block> AZALEA_STAIRS
            = registerBlockItem("azalea_stairs", () -> new WoodenStairsBlock(AZALEA_PLANKS.get().defaultBlockState()));

    public static final Supplier<? extends Block> AZALEA_SLAB
            = registerBlockItem("azalea_slab", WoodenSlabBlock::new);

    public static final Supplier<? extends Block> AZALEA_DOOR
            = registerBlockItem("azalea_door", () -> new DoorBlock(AZALEA, getProperties(Blocks.OAK_DOOR)));

    public static final Supplier<? extends Block> AZALEA_TRAPDOOR
            = registerBlockItem("azalea_trapdoor", () -> new TrapDoorBlock(AZALEA, getProperties(Blocks.OAK_TRAPDOOR)));

    public static final Supplier<? extends Block> AZALEA_FENCE
            = registerBlockItem("azalea_fence", WoodenFenceBlock::new);

    public static final Supplier<? extends Block> AZALEA_FENCE_GATE
            = registerBlockItem("azalea_fence_gate", () -> new WoodenFenceGateBlock(AZALEA_WOOD_TYPE));

    public static final Supplier<? extends Block> AZALEA_PRESSURE_PLATE
            = registerBlockItem("azalea_pressure_plate", () -> new PressurePlateBlock(AZALEA, getProperties(Blocks.OAK_PRESSURE_PLATE)));

    public static final Supplier<? extends Block> AZALEA_BUTTON
            = registerBlockItem("azalea_button", () -> new ButtonBlock(AZALEA, 30, getProperties(Blocks.OAK_BUTTON)));

    // SWEETWOOD

    public static final Supplier<? extends Block> SWEETWOOD_PARTICLE
            = registerBlockItem("sweetwood_particle", SweetwoodParticleBlock::new);

    public static final Supplier<? extends Block> SWEETWOOD_LOG
            = registerBlockItem("sweetwood_log", () -> new SweetwoodLogBlock(getProperties(Blocks.OAK_PLANKS)));

    public static final Supplier<? extends Block> STRIPPED_SWEETWOOD_LOG
            = registerBlockItem("stripped_sweetwood_log", () -> new SweetwoodLogBlock(getProperties(Blocks.OAK_PLANKS)));

    public static final Supplier<? extends Block> STRIPPED_SWEETWOOD_BUNDLE
            = registerBlockItem("stripped_sweetwood_bundle", () -> new WoodenLogBlock(null));

    public static final Supplier<? extends Block> STRIPPED_SWEETWOOD_WOOD
            = registerBlockItem("stripped_sweetwood_wood", () -> new WoodenLogBlock(null));

    public static final Supplier<? extends Block> SWEETWOOD_BUNDLE
            = registerBlockItem("sweetwood_bundle", () -> new WoodenLogBlock(STRIPPED_SWEETWOOD_BUNDLE.get()));

    public static final Supplier<? extends Block> SWEETWOOD_WOOD
            = registerBlockItem("sweetwood_wood", () -> new WoodenLogBlock(STRIPPED_SWEETWOOD_WOOD.get()));

    public static final Supplier<? extends Block> SWEETWOOD_PLANKS
            = registerBlockItem("sweetwood_planks", WoodenPlanksBlock::new);

    public static final Supplier<? extends Block> SWEETWOOD_STAIRS
            = registerBlockItem("sweetwood_stairs", () -> new WoodenStairsBlock(SWEETWOOD_PLANKS.get().defaultBlockState()));

    public static final Supplier<? extends Block> SWEETWOOD_SLAB
            = registerBlockItem("sweetwood_slab", WoodenSlabBlock::new);

    public static final Supplier<? extends Block> SWEETWOOD_DOOR
            = registerBlockItem("sweetwood_door", () -> new DoorBlock(SWEETWOOD, getProperties(Blocks.OAK_DOOR)));

    public static final Supplier<? extends Block> SWEETWOOD_TRAPDOOR
            = registerBlockItem("sweetwood_trapdoor", () -> new TrapDoorBlock(SWEETWOOD, getProperties(Blocks.OAK_TRAPDOOR)));

    public static final Supplier<? extends Block> SWEETWOOD_FENCE
            = registerBlockItem("sweetwood_fence", WoodenFenceBlock::new);

    public static final Supplier<? extends Block> SWEETWOOD_FENCE_GATE
            = registerBlockItem("sweetwood_fence_gate", () -> new WoodenFenceGateBlock(SWEETWOOD_WOOD_TYPE));

    public static final Supplier<? extends Block> SWEETWOOD_PRESSURE_PLATE
            = registerBlockItem("sweetwood_pressure_plate", () -> new PressurePlateBlock(SWEETWOOD, getProperties(Blocks.OAK_PRESSURE_PLATE)));

    public static final Supplier<? extends Block> SWEETWOOD_BUTTON
            = registerBlockItem("sweetwood_button", () -> new ButtonBlock(SWEETWOOD, 30, getProperties(Blocks.OAK_BUTTON)));

    public static final Supplier<? extends Block> TALL_SWEETROOT
            = registerBlockItem("tall_sweetroot", TallSweetrootBlock::new);

    public static final Supplier<? extends Block> SHORT_SWEETROOT
            = registerBlockItem("short_sweetroot", () -> new ShortSweetrootBlock(TALL_SWEETROOT.get()));

    // DEEP DARK

    public static final Supplier<? extends Block> DEEPSLATE_LATTICE
            = registerBlockItem("deepslate_lattice", () -> new Block(getProperties(Blocks.POLISHED_DEEPSLATE)));

    public static final Supplier<? extends Block> STONE_CHEST
            = registerBlockItem("stone_chest", StoneChestBlock::new);

    /*
    public static final Supplier<? extends Block> BRUV
            = registerBlockItem("bruv", () -> new BurnTransformBlock(getProperties(Blocks.POLISHED_DEEPSLATE)));

     */

    // LANTERN KELP

    public static final Supplier<? extends Block> LANTERN_KELP_BULB
            = registerBlockItem("lantern_bulb", LanternBulbBlock::new);

    public static final Supplier<? extends Block> ROTTING_LANTERN_KELP_BULB
            = registerBlockItem("rotting_lantern_bulb", RottingLanternBulbBlock::new);

    public static final Supplier<? extends Block> LANTERN_KELP_HEAD
            = registerBlockItem("lantern_kelp_head", LanternKelpBlock::new);

    public static final Supplier<? extends Block> LANTERN_KELP_BODY
            = BLOCKS.register("lantern_kelp_body", LanternKelpPlantBlock::new);

    public static final Supplier<? extends Block> LANTERN_PASTE
            = BLOCKS.register("lantern_paste", LanternPasteBlock::new);

    // PLANTS

    public static final Supplier<? extends Block> FLOWERING_MOSSPROUT
            = registerBlockItem("flowering_mosssprout", () -> new TallFlowerBlock(getProperties(Blocks.ROSE_BUSH)));

    public static final Supplier<? extends Block> TALL_MOSSSPROUT
            = registerBlockItem("tall_mosssprout", TallGrassBlock::new);

    public static final Supplier<? extends Block> SHORT_MOSSPROUT
            = registerBlockItem("short_mosssprout", () -> new ShortGrassBlock(TALL_MOSSSPROUT.get()));


    public static final Supplier<? extends Block> AZALEA_PETALS
            = registerBlockItem("azalea_petals", () -> new BonemealableMultiFaceBlock(getProperties(Blocks.PINK_PETALS)));

    // FOSSIL

    public static final Supplier<? extends Block> FOSSIL_BLOCK
            = registerBlockItem("fossil_block", () -> new RotatedPillarBlock(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> POLISHED_FOSSIL_BLOCK
            = registerBlockItem("polished_fossil_block", () -> new Block(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> POLISHED_FOSSIL_BLOCKS_STAIRS
            = registerBlockItem("polished_fossil_block_stairs", () -> new StairBlock(POLISHED_FOSSIL_BLOCK.get().defaultBlockState(), getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> POLISHED_FOSSIL_BLOCKS_SLAB
            = registerBlockItem("polished_fossil_block_slab", () -> new SlabBlock(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> POLISHED_FOSSIL_BLOCKS_WALL
            = registerBlockItem("polished_fossil_block_wall", () -> new WallBlock(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> FOSSIL_BRICKS
            = registerBlockItem("fossil_bricks", () -> new Block(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> CRACKED_FOSSIL_BRICKS
            = registerBlockItem("cracked_fossil_bricks", () -> new Block(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> FOSSIL_BRICK_STAIRS
            = registerBlockItem("fossil_brick_stairs", () -> new StairBlock(FOSSIL_BRICKS.get().defaultBlockState(), getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> FOSSIL_BRICK_SLAB
            = registerBlockItem("fossil_brick_slab", () -> new SlabBlock(getProperties(Blocks.BONE_BLOCK)));

    public static final Supplier<? extends Block> FOSSIL_BRICK_WALL
            = registerBlockItem("fossil_brick_wall", () -> new WallBlock(getProperties(Blocks.BONE_BLOCK)));

    //

    public static final Supplier<? extends Block> FLOWERING_VINES_HEAD = registerBlockItem("flowering_vines_head", FloweringVinesBlock::new);
    public static final Supplier<Block> FLOWERING_VINES_BODY = BLOCKS.register("flowering_vines_body", FloweringVinesPlantBlock::new);

    public static final Supplier<? extends Block> AIR_BUD = registerBlockItem("air_bud", AirBudBlock::new);
    public static final Supplier<? extends Block> WAILING_STONE = registerBlockItem("wailing_stone", WailingStoneBlock::new);
    public static final Supplier<? extends Block> SANCTUARY_GUARDIAN = registerBlockItem("sanctuary_guardian", SanctuaryGuardianBlock::new);
    public static final Supplier<? extends Block> DETERIORATED_CRAFTING_TABLE = registerBlockItem("deteriorated_crafting_table", DeterioratedCraftingTableBlock::new);


    public static final Supplier<? extends Block> CAVE_SOIL = registerBlockItem(
            "cave_soil", () -> new CanSustainPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));

    public static final Supplier<? extends Block> BRAIDED_ROOTS = registerBlockItem(
            "braided_roots", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));

    public static final Supplier<? extends Block> CAVE_ROOTS = registerBlockItem(
            "cave_roots", () -> new Block(BlockBehaviour.Properties.of().noOcclusion()));

    public static final Supplier<? extends Block> INFESTED_BRAIDED_ROOTS = registerBlockItem(
            "infested_braided_roots", InfestedBraidedRootBlock::new);

    public static final Supplier<? extends Block> SHADER_BLOCK
            = registerBlockItem("shader_block", () -> new ShaderBlock(getProperties(Blocks.POLISHED_DEEPSLATE)));

    // HELPERS

    // registers block and item if class extends block
    private static Supplier<? extends Block> registerBlockItem(String resourceLocation, Supplier<? extends Block> blockType) {
        var tempBlock = BLOCKS.register(resourceLocation, blockType);
        BLOCKITEMS.registerSimpleBlockItem(tempBlock);
        return tempBlock;
    }

    @SuppressWarnings("unchecked")
    // specifically for block
    private static Supplier<Block> registerBlockItem(String resourceLocation) {
        return (Supplier<Block>) registerBlockItem(resourceLocation, () -> new Block(BlockBehaviour.Properties.of()));
    }

    // returns block properties
    private static BlockBehaviour.Properties getProperties(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }
}
