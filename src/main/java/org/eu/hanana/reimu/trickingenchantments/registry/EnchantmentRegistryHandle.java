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
import org.eu.hanana.reimu.trickingenchantments.enchantment.*;

public class EnchantmentRegistryHandle{
    public static DeferredRegister<Enchantment> ENCHANTMENTS;
    public static DeferredHolder<Enchantment, ? extends Enchantment> NOT_SHARP;
    public static DeferredHolder<Enchantment, ? extends Enchantment> SOUL_SLOW;
    public static DeferredHolder<Enchantment, ? extends Enchantment> Breaking;
    public static DeferredHolder<Enchantment, ? extends Enchantment> NoMending;
    public static DeferredHolder<Enchantment, ? extends Enchantment> HARM;
    public static DeferredHolder<Enchantment, ? extends Enchantment> HARM_EXPLOSION;
    public static DeferredHolder<Enchantment, ? extends Enchantment> HARM_FIRE;
    public static DeferredHolder<Enchantment, ? extends Enchantment> HARM_PROJECTILE;
    public static DeferredHolder<Enchantment, ? extends Enchantment> HARM_FALL;
    // 确保你正确设置了 ResourceLocation

    public static void reg(IEventBus modEventBus){
        ENCHANTMENTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT, TrickingEnchantmentsMod.MOD_ID);

        NOT_SHARP = ENCHANTMENTS.register("notsharp", NotSharpEnchantment::new);
        SOUL_SLOW = ENCHANTMENTS.register("soul_slow", SoulSlowEnchantment::new);
        Breaking = ENCHANTMENTS.register("breaking", BreakingEnchantment::new);
        NoMending = ENCHANTMENTS.register("no_mending", NoMendingEnchantment::new);
        HARM = ENCHANTMENTS.register(HarmEnchantment.HARM.getPath(), HarmEnchantment::create);
        HARM_EXPLOSION = ENCHANTMENTS.register(HarmEnchantment.HARM_EXPLOSION.getPath(), HarmEnchantment::create);
        HARM_FALL = ENCHANTMENTS.register(HarmEnchantment.HARM_FALL.getPath(), HarmEnchantment::create);
        HARM_FIRE = ENCHANTMENTS.register(HarmEnchantment.HARM_FIRE.getPath(), HarmEnchantment::create);
        HARM_PROJECTILE = ENCHANTMENTS.register(HarmEnchantment.HARM_PROJECTILE.getPath(), HarmEnchantment::create);

        ENCHANTMENTS.register(modEventBus);
    }
}
