package org.eu.hanana.reimu.trickingenchantments.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.SoulSpeedEnchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.eu.hanana.reimu.trickingenchantments.TrickingEnchantmentsMod;
import org.eu.hanana.reimu.trickingenchantments.enchantment.BreakingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.enchantment.NotSharpEnchantment;
import org.eu.hanana.reimu.trickingenchantments.enchantment.SoulSlowEnchantment;

public class EnchantmentRegistryHandle {
    public static DeferredRegister<Enchantment> ENCHANTMENTS;
    public static DeferredHolder<Enchantment, ? extends Enchantment> NOT_SHARP;
    public static DeferredHolder<Enchantment, ? extends Enchantment> SOUL_SLOW;
    public static DeferredHolder<Enchantment, ? extends Enchantment> Breaking;
    // 确保你正确设置了 ResourceLocation

    public static void reg(IEventBus modEventBus){
        ENCHANTMENTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT, TrickingEnchantmentsMod.MOD_ID);

        NOT_SHARP = ENCHANTMENTS.register("notsharp", NotSharpEnchantment::new);
        SOUL_SLOW = ENCHANTMENTS.register("soul_slow", SoulSlowEnchantment::new);
        Breaking = ENCHANTMENTS.register("breaking", BreakingEnchantment::new);

        ENCHANTMENTS.register(modEventBus);
    }
}
