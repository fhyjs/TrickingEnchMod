package org.eu.hanana.reimu.trickingenchantments;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.Bindings;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.eu.hanana.reimu.trickingenchantments.client.ClientProxy;
import org.eu.hanana.reimu.trickingenchantments.common.CommonProxy;
import org.eu.hanana.reimu.trickingenchantments.event.EventHandle;
import org.eu.hanana.reimu.trickingenchantments.event.ModEventBus;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;
import org.slf4j.Logger;

@Mod(TrickingEnchantmentsMod.MOD_ID)
public class TrickingEnchantmentsMod {
    public static final String MOD_ID = "trickingenchantments";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static TrickingEnchantmentsMod INSTANCE;
    public final CommonProxy proxy;
    public TrickingEnchantmentsMod(IEventBus modEventBus)
    {
        TrickingEnchantmentsMod.INSTANCE=this;
        if (FMLLoader.getDist().isClient()){
            proxy=new ClientProxy();
        }else {
            proxy=new CommonProxy();
        }
        // Register the commonSetup method for modloading
        //modEventBus.addListener(proxy::equals);

        // Register the Deferred Register to the mod event bus so blocks get registered
        //BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        //ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        //CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);
        modEventBus.register(new ModEventBus());

        // Register the item to a creative tab
        //modEventBus.addListener(this::addCreative);
        EnchantmentRegistryHandle.reg(modEventBus);
        Bindings.getForgeBus().get().register(new EventHandle());

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }
}
