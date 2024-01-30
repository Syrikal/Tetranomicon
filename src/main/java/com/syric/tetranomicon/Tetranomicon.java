package com.syric.tetranomicon;

import com.syric.tetranomicon.registry.TetranomiconItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Tetranomicon.MODID)
public class Tetranomicon {
    public static final String MODID = "tetranomicon";
    public static final Logger LOGGER = LogManager.getLogger();

    public Tetranomicon () {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (ModList.get().isLoaded("oresabovediamonds")) {
            TetranomiconItems.ITEMS.register(modEventBus);
        }

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.register(new Debugging());


    }
}
