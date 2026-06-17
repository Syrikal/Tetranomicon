package com.syric.tetranomicon.registry;

import com.syric.tetranomicon.Tetranomicon;
import com.syric.tetranomicon.compat.BetterNetherCompat;
import com.syric.tetranomicon.compat.OresAboveDiamondsCompat;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(
        modid = "tetranomicon",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class TetranomiconItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tetranomicon.MODID);
    public static final RegistryObject<Item> NETHERITE_OPAL = ITEMS.register("netherite_opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CINCINNASITE_DIAMOND = ITEMS.register("cincinnasite_diamond", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLAMING_RUBY = ITEMS.register("flaming_ruby", () -> new Item(new Item.Properties()));

    public TetranomiconItems() {}

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        if (ModList.get().isLoaded("oresabovediamonds")) {
            OresAboveDiamondsCompat.buildOresAboveDiamondsCreativeModeTabs(event);
        }
        if (ModList.get().isLoaded("betternether")) {
            BetterNetherCompat.buildBetterNetherCreativeModeTabs(event);
        }
    }
}
