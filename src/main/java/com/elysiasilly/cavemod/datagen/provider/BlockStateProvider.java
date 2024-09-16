package com.elysiasilly.cavemod.datagen.provider;

import com.elysiasilly.cavemod.CaveMod;
import com.elysiasilly.cavemod.registry.BlockRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CaveMod.MODID, exFileHelper);
    }




    @Override
    protected void registerStatesAndModels() {

        simpleBlockWithItem(BlockRegistry.VELVET_MOSS_BLOCK.get(), cubeAll(BlockRegistry.VELVET_MOSS_BLOCK.get()));

        simpleBlockWithItem(BlockRegistry.DEEPSLATE_LATTICE.get(), cubeAll(BlockRegistry.DEEPSLATE_LATTICE.get()));


        simpleBlock(BlockRegistry.SHORT_VELVET_MOSSSPROUT.get(), models().cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.SHORT_VELVET_MOSSSPROUT.get()).getPath(), blockTexture(BlockRegistry.SHORT_VELVET_MOSSSPROUT.get())).renderType("cutout"));
        //simpleBlockWithItem(CMBlock.VELVET_FLOWER.get(), models().cross(BuiltInRegistries.BLOCK.getKey(CMBlock.VELVET_FLOWER.get()).getPath(), blockTexture(CMBlock.VELVET_FLOWER.get())).renderType("cutout"));

        //simpleBlockWithItem(CMBlock.TALL_VELVET_MOSSPROUT.get(), m);

        logBlock((RotatedPillarBlock) BlockRegistry.AZALEA_LOG.get());
        simpleBlockItem(BlockRegistry.AZALEA_LOG.get(), models().withExistingParent("azalea_log", "cube_column"));

        logBlock((RotatedPillarBlock) BlockRegistry.STRIPPED_AZALEA_LOG.get());
        simpleBlockItem(BlockRegistry.STRIPPED_AZALEA_LOG.get(), models().withExistingParent("stripped_azalea_log", "cube_column"));

        //simpleBlockWithItem(CMBlock.VELVET_MOSS_CARPET.get(), this.);

        //simpleBlockWithItem(CMBlock.VELVET_MOSS_CARPET.get(), BlockModelGenerators.);

        simpleBlockWithItem(BlockRegistry.AZALEA_PLANKS.get(), cubeAll(BlockRegistry.AZALEA_PLANKS.get()));

        simpleBlock(BlockRegistry.LANTERN_KELP_HEAD.get(), models().cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.LANTERN_KELP_HEAD.get()).getPath(), blockTexture(BlockRegistry.LANTERN_KELP_HEAD.get())).renderType("cutout"));
        simpleBlock(BlockRegistry.LANTERN_KELP_BODY.get(), models().cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.LANTERN_KELP_BODY.get()).getPath(), blockTexture(BlockRegistry.LANTERN_KELP_BODY.get())).renderType("cutout"));

    }



}
