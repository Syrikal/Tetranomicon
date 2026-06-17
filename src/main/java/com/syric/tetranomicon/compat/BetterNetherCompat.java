package com.syric.tetranomicon.compat;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import static com.syric.tetranomicon.registry.TetranomiconItems.CINCINNASITE_DIAMOND;
import static com.syric.tetranomicon.registry.TetranomiconItems.FLAMING_RUBY;

public class BetterNetherCompat {

    public static void buildBetterNetherCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab.location().toString().equals("betternether:items_tab")) {
            event.getEntries().put(new ItemStack(CINCINNASITE_DIAMOND.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().put(new ItemStack(FLAMING_RUBY.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

}
