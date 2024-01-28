package com.syric.tetranomicon;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.ConfigHandler;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.module.data.MaterialData;

public class Debugging {

//    @SubscribeEvent
//    void addToTooltip(RenderTooltipEvent.@NotNull GatherComponents event) {
//        if (!ConfigHandler.development.get()) {
//            return;
//        }
//        List<Either<FormattedText, TooltipComponent>> components = event.getTooltipElements();
//        String item_id = event.getItemStack().getItem().toString();
//        String mod_id = event.getItemStack().getItem().getCreatorModId(event.getItemStack());
//        mod_id = mod_id == null ? " " : mod_id;
//        ItemStack stack = event.getItemStack();
//
//        boolean likelyCandidateTags = (stack.is(Tags.Items.STONE) || stack.is(ItemTags.PLANKS) || stack.is(Tags.Items.INGOTS) || stack.is(Tags.Items.GEMS));
//        boolean likelyCandidateID = (item_id.contains("planks") || item_id.contains("ingot"));
//        boolean minecraft = mod_id.contains("minecraft");
//        boolean likelyCandidate = likelyCandidateID || likelyCandidateTags && !minecraft;
//        boolean isTetraMaterial = DataManager.instance.materialData.getData().values().stream().anyMatch(x -> {
//            ItemPredicate predicate = x.material.getPredicate();
//            if (predicate != null) {
//                if (x.material.isTagged()) {
//                    return false;
//                }
//                return predicate.matches(stack);
//            }
//            return false;
//        });
//
//        if (likelyCandidate && !isTetraMaterial) {
//            Component newComponent = Component.literal("LIKELY CANDIDATE IS NOT MATERIAL!").withStyle(ChatFormatting.RED);
//            Either<FormattedText, TooltipComponent> eitherComponent = Either.left(newComponent);
//            components.add(eitherComponent);
//        }
//        if (likelyCandidate) {
//            Component newComponent = Component.literal("Item is a likely candidate").withStyle(ChatFormatting.BLUE);
//            Either<FormattedText, TooltipComponent> eitherComponent = Either.left(newComponent);
//            components.add(eitherComponent);
//        }
//        if (isTetraMaterial) {
//            Component newComponent = Component.literal("Item is a Tetra material!").withStyle(ChatFormatting.GREEN);
//            Either<FormattedText, TooltipComponent> eitherComponent = Either.left(newComponent);
//            components.add(eitherComponent);
//        }
//
//    }

    @SubscribeEvent
    void checkMissingMaterials(ServerChatEvent event) {
        if (!ConfigHandler.development.get()) {
            return;
        }
        String rawText = event.getMessage();
        if (rawText.contains("tetranomicon check missing")) {
            Tetranomicon.LOGGER.debug("Tetranomicon: scanning for missing materials...");
            for (MaterialData materialData : DataManager.instance.materialData.getData().values()) {
//                Tetranomicon.LOGGER.debug("Checking material " + materialData.key);
                if (materialData.material.getApplicableItemStacks().length == 0) {
                    Tetranomicon.LOGGER.debug("Material missing item: " + materialData.key);
                }
            }
        }
    }

    @SubscribeEvent
    void checkCandidates(ServerChatEvent event) {
        if (!ConfigHandler.development.get()) {
            return;
        }
        String rawText = event.getMessage();
        if (rawText.contains("tetranomicon check candidates")) {
            Tetranomicon.LOGGER.debug("Tetranomicon: scanning for unused candidates...");

            ForgeRegistries.ITEMS.getEntries().stream()
//                    .peek(x -> Tetranomicon.LOGGER.debug("Checking item " + x.getValue().toString()))
                    .filter(x -> {
                        ItemStack stack = x.getValue().getDefaultInstance();
                        String item_id = x.getValue().toString();
                        String mod_id = x.getValue().getCreatorModId(stack);
                        boolean likelyCandidateTags = (stack.is(Tags.Items.STONE) || stack.is(ItemTags.PLANKS) || stack.is(Tags.Items.INGOTS) || stack.is(Tags.Items.GEMS));
                        boolean likelyCandidateID = (item_id.contains("planks") || item_id.contains("ingot")) || item_id.contains("vine") || item_id.contains("bone");
                        boolean minecraft = mod_id == null || mod_id.contains("minecraft");
                        return likelyCandidateID || likelyCandidateTags && !minecraft;
                    })
                    .filter(x -> DataManager.instance.materialData.getData().values().stream().noneMatch(y -> {
                        ItemPredicate predicate = y.material.getPredicate();
                        if (predicate != null) {
                            if (y.material.isTagged()) {
                                return false;
                            }
                            return predicate.matches(x.getValue().getDefaultInstance());
                        }
                        return false;
                    }))
                    .filter(x -> !x.getValue().toString().contains("vertical"))
                    .filter(x -> !x.getValue().toString().contains("dyed"))
                    .filter(x -> !x.getValue().toString().contains("polished"))
                    .filter(x -> !x.getValue().toString().contains("slab"))
                    .filter(x -> !x.getValue().toString().contains("stairs"))
                    .filter(x -> !x.getValue().toString().contains("painted"))
                    .filter(x -> !x.getValue().toString().contains("mossy"))
                    .filter(x -> !x.getValue().toString().contains("olivine"))
                    .forEach(x -> Tetranomicon.LOGGER.debug("Candidate that is not a material: " + x.getValue().getCreatorModId(x.getValue().getDefaultInstance()) + ":" + x.getValue()));
        }
    }



}
