package com.syric.tetranomicon.registry;

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

public class TetranomiconTiers {

    public static void init() {
        TagKey<Block> sevenTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_seven_tool"));
        Tier sevenTier = TierSortingRegistry.registerTier(new ForgeTier(Tiers.NETHERITE.getLevel() + 1, 0, 0, 0, 0,
                sevenTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_seven"), List.of(Tiers.NETHERITE), List.of());

        TagKey<Block> eightTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_eight_tool"));
        Tier eightTier = TierSortingRegistry.registerTier(new ForgeTier(Tiers.NETHERITE.getLevel() + 2, 0, 0, 0, 0,
                eightTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_eight"), List.of(sevenTier), List.of());

        TagKey<Block> nineTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_nine_tool"));
        Tier nineTier = TierSortingRegistry.registerTier(new ForgeTier(Tiers.NETHERITE.getLevel() + 3, 0, 0, 0, 0,
                nineTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_nine"), List.of(eightTier), List.of());

        TagKey<Block> tenTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_ten_tool"));
        Tier tenTier = TierSortingRegistry.registerTier(new ForgeTier(Tiers.NETHERITE.getLevel() + 4, 0, 0, 0, 0,
                tenTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_ten"), List.of(nineTier), List.of());

        TagKey<Block> elevenTag = BlockTags.create(new ResourceLocation("tetranomicon:needs_tier_eleven_tool"));
        Tier elevenTier = TierSortingRegistry.registerTier(new ForgeTier(Tiers.NETHERITE.getLevel() + 5, 0, 0, 0, 0,
                elevenTag, () -> Ingredient.EMPTY), new ResourceLocation("tetranomicon:tier_eleven"), List.of(tenTier), List.of());
    }

}
