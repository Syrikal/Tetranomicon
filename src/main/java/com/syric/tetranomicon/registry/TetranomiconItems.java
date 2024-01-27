package com.syric.tetranomicon.registry;

import com.syric.tetranomicon.Tetranomicon;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oresAboveDiamonds.init.ModItemGroups;


public class TetranomiconItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tetranomicon.MODID);
    public static final RegistryObject<Item> NETHERITE_OPAL = ITEMS.register("netherite_opal", () -> new Item(new Item.Properties().tab(ModItemGroups.OAD_ITEM_GROUP)));

}
