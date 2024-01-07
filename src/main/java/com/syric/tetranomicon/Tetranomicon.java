package com.syric.tetranomicon;

import com.syric.tetranomicon.registry.TetranomiconItems;
import com.syric.tetranomicon.registry.TetranomiconTiers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Tetranomicon.MODID)
public class Tetranomicon {
    public static final String MODID = "tetranomicon";

    public Tetranomicon () {
        MinecraftForge.EVENT_BUS.register(this);
        TetranomiconTiers.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (ModList.get().isLoaded("oresabovediamonds")) {
            TetranomiconItems.ITEMS.register(modEventBus);
        }
    }
}
