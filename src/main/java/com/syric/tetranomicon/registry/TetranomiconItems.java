package com.syric.tetranomicon.registry;

import com.syric.tetranomicon.Tetranomicon;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oresAboveDiamonds.init.ModItemGroups;
import oresAboveDiamonds.init.ModItems;

@Mod.EventBusSubscriber(
        modid = "tetranomicon",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class TetranomiconItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tetranomicon.MODID);
    public static final RegistryObject<Item> NETHERITE_OPAL = ITEMS.register("netherite_opal", () -> new Item(new Item.Properties()));

    public TetranomiconItems() {
    }

    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (ModList.get().isLoaded("oresabovediamonds")) {
            if (tab == ModItemGroups.OAD_TAB.getKey()) {
                event.getEntries().putBefore(new ItemStack(ModItems.BLACK_OPAL.get()), new ItemStack(NETHERITE_OPAL.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

}
