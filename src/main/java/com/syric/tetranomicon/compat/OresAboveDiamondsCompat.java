package com.syric.tetranomicon.compat;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import oresAboveDiamonds.init.ModItemGroups;
import oresAboveDiamonds.init.ModItems;

import static com.syric.tetranomicon.registry.TetranomiconItems.*;

public class OresAboveDiamondsCompat {

    public static void buildOresAboveDiamondsCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab == ModItemGroups.OAD_TAB.getKey()) {
            event.getEntries().putBefore(new ItemStack(ModItems.BLACK_OPAL.get()), new ItemStack(NETHERITE_OPAL.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

}
