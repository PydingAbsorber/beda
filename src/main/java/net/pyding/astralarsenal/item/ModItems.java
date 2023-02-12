package net.pyding.astralarsenal.item;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.pyding.astralarsenal.AstralArsenalMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "astralarsenal");

    public static final RegistryObject<Item> AGGRO = ITEMS.register("aggro",
            () -> new Aggro(new Item.Properties().group(AstralArsenalMod.ASTRALARSENAL_GROUP)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
