package com.syric.tetranomicon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.ConfigHandler;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.module.data.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Debugging {

    @SubscribeEvent
    void addToTooltip(ItemTooltipEvent event) {
        if (true) {
            return;
        }
        if (!ConfigHandler.development.get()) {
            return;
        }
        List<ITextComponent> components = event.getToolTip();
        String item_id = event.getItemStack().getItem().toString();
        String mod_id = event.getItemStack().getItem().getCreatorModId(event.getItemStack());
        mod_id = mod_id == null ? " " : mod_id;
        ItemStack stack = event.getItemStack();

        boolean likelyCandidateTags = (stack.getItem().is(Tags.Items.STONE) || stack.getItem().is(ItemTags.PLANKS) || stack.getItem().is(Tags.Items.INGOTS) || stack.getItem().is(Tags.Items.GEMS));
        boolean likelyCandidateID = (item_id.contains("planks") || item_id.contains("ingot"));
        boolean minecraft = mod_id.contains("minecraft");
        boolean likelyCandidate = likelyCandidateID || likelyCandidateTags && !minecraft;
        boolean isTetraMaterial = DataManager.materialData.getData().values().stream().anyMatch(x -> {
            ItemPredicate predicate = x.material.getPredicate();
            if (predicate != null) {
                if (x.material.isTagged()) {
                    return false;
                }
                return predicate.matches(stack);
            }
            return false;
        });

        if (likelyCandidate && !isTetraMaterial) {
            TextComponent newComponent = (TextComponent) new StringTextComponent("LIKELY CANDIDATE IS NOT MATERIAL!").withStyle(TextFormatting.RED);
            components.add(newComponent);
        }
        if (likelyCandidate) {
            TextComponent newComponent = (TextComponent) new StringTextComponent("Item is a likely candidate").withStyle(TextFormatting.BLUE);
            components.add(newComponent);
        }
        if (isTetraMaterial) {
            TextComponent newComponent = (TextComponent) new StringTextComponent("Item is a Tetra material!").withStyle(TextFormatting.GREEN);
            components.add(newComponent);
        }

    }

    @SubscribeEvent
    void checkMissingMaterials(ServerChatEvent event) {
        if (!ConfigHandler.development.get()) {
            return;
        }
        String rawText = event.getMessage();
        if (rawText.contains("tetranomicon check missing")) {
            Tetranomicon.LOGGER.debug("Tetranomicon: scanning for missing materials...");
            for (MaterialData materialData : DataManager.materialData.getData().values()) {
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
                        boolean likelyCandidateTags = (stack.getItem().is(Tags.Items.STONE) || stack.getItem().is(ItemTags.PLANKS) || stack.getItem().is(Tags.Items.INGOTS) || stack.getItem().is(Tags.Items.GEMS));
                        boolean likelyCandidateID = (item_id.contains("planks") || item_id.contains("ingot")) || item_id.contains("vine") || item_id.contains("bone");
                        boolean minecraft = mod_id == null || mod_id.contains("minecraft");
                        return likelyCandidateID || likelyCandidateTags && !minecraft;
                    })
                    .filter(x -> DataManager.materialData.getData().values().stream().noneMatch(y -> {
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

    @SubscribeEvent
    void checkMissingReplacements(ServerChatEvent event) {
        if (!ConfigHandler.development.get()) {
            return;
        }
        String rawText = event.getMessage();
        if (rawText.contains("tetranomicon missing replacements")) {
            Tetranomicon.LOGGER.debug("Tetranomicon: scanning for missing replacements...");
            DataManager.replacementData.getData().values().forEach(replacementList -> Arrays.stream(replacementList).filter(replacement ->
                ForgeRegistries.ITEMS.getEntries().stream().noneMatch(item -> replacement.predicate.matches(item.getValue().getDefaultInstance())))
                    .forEach(replacement -> {
                        JsonObject jsonObject = replacement.predicate.serializeToJson().getAsJsonObject();
                        JsonElement items = jsonObject.get("items");
                        StringBuilder itemsList = new StringBuilder();
                        if (items instanceof JsonArray jsonArray) {
                            for (JsonElement item : jsonArray) {
                                itemsList.append(item.getAsString());
                                itemsList.append(", ");
                            }
                        }
                        if (items != null) {
                            Tetranomicon.LOGGER.debug("Replacement missing item: " + replacement.itemStack.toString() + " / " + itemsList);
                        }
                    }));
        }
    }

    @SubscribeEvent
    void checkReplacementCandidates(ServerChatEvent event) {
        if (!ConfigHandler.development.get()) {
            return;
        }
        String rawText = event.getMessage();
        if (rawText.contains("tetranomicon check replacement candidates")) {
            Tetranomicon.LOGGER.debug("Tetranomicon: scanning for replacement candidates...");

//            ArrayList<TagKey<Item>> tagKeys = new ArrayList<>(Arrays.asList(ItemTags..Items.TOOLS, Tags.Items.TOOLS_AXES, Tags.Items.TOOLS_PICKAXES, Tags.Items.TOOLS_SHOVELS, Tags.Items.TOOLS_HOES, Tags.Items.TOOLS_SWORDS, Tags.Items.TOOLS_BOWS));
            ArrayList<String> tool_names = new ArrayList<>(Arrays.asList("_axe", "_pickaxe", "_shovel", "_pick", "_hoe", "_sword", "_bow"));

            ForgeRegistries.ITEMS.getEntries().stream()
                    .filter(x -> {
                        Item item = x.getValue();
                        ItemStack stack = item.getDefaultInstance();
                        String item_id = item.toString();
                        String mod_id = item.getCreatorModId(stack);

//                        boolean likelyCandidateTags = tagKeys.stream().anyMatch(stack::is);
                        boolean likelyCandidateStrings = tool_names.stream().anyMatch(item_id::contains);
                        boolean minecraft = mod_id == null || mod_id.contains("minecraft");

                        return likelyCandidateStrings && !minecraft;
                    })
                    .filter(x -> DataManager.replacementData.getData().values().stream().noneMatch(replacementList -> {
                        ItemStack stack = x.getValue().getDefaultInstance();
                        return Arrays.stream(replacementList).anyMatch(replacement -> replacement.predicate.matches(stack));
                    }))
                    .forEach(x -> Tetranomicon.LOGGER.debug("Candidate tool not replaced: " + x.getValue().getCreatorModId(x.getValue().getDefaultInstance()) + ":" + x.getValue()));
        }
    }



}
