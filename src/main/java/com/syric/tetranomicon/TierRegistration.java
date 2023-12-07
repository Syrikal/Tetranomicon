package com.syric.tetranomicon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import se.mickelus.tetra.TetraRegistries;

import java.util.List;

public class TierRegistration {

    public static void init() {
        TagKey<Block> sixTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_six_tool"));
        Tier sixTier = TierSortingRegistry.registerTier(new ForgeTier(TetraRegistries.forgeHammerTier.getLevel() + 1, 0, 0, 0, 0,
                sixTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_six"), List.of(TetraRegistries.forgeHammerTier), List.of());
        TagKey<Block> sevenTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_seven_tool"));
        Tier sevenTier = TierSortingRegistry.registerTier(new ForgeTier(sixTier.getLevel() + 1, 0, 0, 0, 0,
                sevenTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_seven"), List.of(sixTier), List.of());
    }

}
