package com.tetranomicon.tetranomicon;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("tetranomicon")
public class Tetranomicon {
    public Tetranomicon () {
        MinecraftForge.EVENT_BUS.register(this);
        TierRegistration.init();
    }
}
