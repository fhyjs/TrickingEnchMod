package org.eu.hanana.reimu.trickingenchantments.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.eu.hanana.reimu.trickingenchantments.TrickingEnchantmentsMod;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;

public class ModEventBus implements IModBusEvent {
    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event){

    }
}
