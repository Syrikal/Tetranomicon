package com.syric.tetranomicon.debugging;

import com.mojang.datafixers.util.Either;
import com.syric.tetranomicon.Tetranomicon;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import se.mickelus.tetra.ConfigHandler;
import se.mickelus.tetra.data.DataManager;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Tetranomicon.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientDebugging {

    @SubscribeEvent
    public static void addToTooltip(RenderTooltipEvent.@NotNull GatherComponents event) {
        if (true) {
            return;
        }
        if (!ConfigHandler.development.get()) {
            return;
        }
        List<Either<FormattedText, TooltipComponent>> components = event.getTooltipElements();
        String item_id = event.getItemStack().getItem().toString();
        String mod_id = event.getItemStack().getItem().getCreatorModId(event.getItemStack());
        mod_id = mod_id == null ? " " : mod_id;
        ItemStack stack = event.getItemStack();

        boolean likelyCandidateTags = (stack.is(Tags.Items.STONE) || stack.is(ItemTags.PLANKS) || stack.is(Tags.Items.INGOTS) || stack.is(Tags.Items.GEMS));
        boolean likelyCandidateID = (item_id.contains("planks") || item_id.contains("ingot"));
        boolean minecraft = mod_id.contains("minecraft");
        boolean likelyCandidate = likelyCandidateID || likelyCandidateTags && !minecraft;
        boolean isTetraMaterial = DataManager.instance.materialData.getData().values().stream().anyMatch(x -> {
            ItemPredicate predicate = x.material.getPredicate();
            if (predicate != null) {
                if (x.material.isTagged()) {
                    return false;
                }
                return predicate.matches(stack);
            }
            return false;
        });

        if (likelyCandidate) {
            Component newComponent = Component.literal("Item is a likely candidate").withStyle(ChatFormatting.BLUE);
            Either<FormattedText, TooltipComponent> eitherComponent = Either.left(newComponent);
            components.add(eitherComponent);
        }
        if (likelyCandidate && !isTetraMaterial) {
            Component newComponent = Component.literal("LIKELY CANDIDATE IS NOT MATERIAL!").withStyle(ChatFormatting.RED);
            Either<FormattedText, TooltipComponent> eitherComponent = Either.left(newComponent);
            components.add(eitherComponent);
        }
        if (isTetraMaterial) {
            Component newComponent = Component.literal("Item is a Tetra material!").withStyle(ChatFormatting.GREEN);
            Either<FormattedText, TooltipComponent> eitherComponent = Either.left(newComponent);
            components.add(eitherComponent);
        }

    }

}
