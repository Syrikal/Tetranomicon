package com.tetranomicon.tetranomicon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class TierRegistration {

    public static void init() {
        TagKey<Block> netheriteTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_netherite_tool"));
        Tier netheriteTier = TierSortingRegistry.registerTier(new ForgeTier(4, 0, 0, 0, 0,
                netheriteTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:netherite"), List.of(Tiers.NETHERITE), List.of());

        TagKey<Block> netheritePlusTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_netherite_plus_tool"));
        Tier netheritePlusTier = TierSortingRegistry.registerTier(new ForgeTier(netheriteTier.getLevel(), 0, 0, 0, 0,
                netheritePlusTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:netherite_plus"), List.of(netheriteTier), List.of());

        TagKey<Block> netheritePlusTwoTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_netherite_plus_two_tool"));
        Tier netheritePlusTwoTier = TierSortingRegistry.registerTier(new ForgeTier(netheritePlusTier.getLevel() + 1, 0, 0, 0, 0,
                netheritePlusTwoTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:netherite_plus_two"), List.of(netheritePlusTier), List.of());
    }

}
