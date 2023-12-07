package com.syric.tetranomicon;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Tetranomicon.MODID)
public class Tetranomicon {
    public static final String MODID = "tetranomicon";

    public Tetranomicon () {
        MinecraftForge.EVENT_BUS.register(this);
        TierRegistration.init();
    }
}
