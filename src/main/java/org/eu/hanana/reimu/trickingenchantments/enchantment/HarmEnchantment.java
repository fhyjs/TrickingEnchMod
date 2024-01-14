package org.eu.hanana.reimu.trickingenchantments.enchantment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.eu.hanana.reimu.trickingenchantments.TrickingEnchantmentsMod;
import org.eu.hanana.reimu.trickingenchantments.common.Datas;
import org.eu.hanana.reimu.trickingenchantments.common.Utils;

public class HarmEnchantment extends ProtectionEnchantment implements ITrickingEnchantment{
    public static final ResourceLocation HARM = new ResourceLocation(TrickingEnchantmentsMod.MOD_ID,"harm");
    public static final ResourceLocation HARM_FIRE = new ResourceLocation(TrickingEnchantmentsMod.MOD_ID,"harm_fire");
    public static final ResourceLocation HARM_FALL = new ResourceLocation(TrickingEnchantmentsMod.MOD_ID,"harm_fall");
    public static final ResourceLocation HARM_EXPLOSION = new ResourceLocation(TrickingEnchantmentsMod.MOD_ID,"harm_explosion");
    public static final ResourceLocation HARM_PROJECTILE = new ResourceLocation(TrickingEnchantmentsMod.MOD_ID,"harm_projectile");
    public HarmEnchantment(ProtectionEnchantment.Type type) {
        super(Rarity.VERY_RARE,type, Datas.ARMOR_SLOTS);
    }
    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        return 0;
    }
    @Override
    public boolean checkCompatibility(Enchantment pEnch) {
        if (pEnch instanceof HarmEnchantment harmEnchantment) {
            if (this.type == harmEnchantment.type) {
                return false;
            } else {
                return this.type == ProtectionEnchantment.Type.FALL || harmEnchantment.type == ProtectionEnchantment.Type.FALL;
            }
        } else {
            return super.checkCompatibility(pEnch);
        }
    }
    public static void fire(LivingEntity entity , DamageSource pSource, float amount){
        MutableInt mutableint = new MutableInt();
        Iterable<ItemStack> armorSlots = entity.getArmorSlots();
        Utils.runIterationOnInventory((enchantment, pLevel) -> {
            if (enchantment instanceof HarmEnchantment harmEnchantment)
                mutableint.add(harmEnchantment.getDamageBounced(pLevel, pSource));
        }, armorSlots);
        int k = mutableint.intValue();
        float pDamageAmount = amount;
        if (k > 0) {
            pDamageAmount += amount*(k*0.5f);
        }
        entity.hurt(pSource,pDamageAmount);
    }
    public int getDamageBounced(int pLevel, DamageSource pSource){
        int l;
        if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            l = 0;
        } else if (type == ProtectionEnchantment.Type.ALL) {
            l = pLevel;
        } else if (type == ProtectionEnchantment.Type.FIRE && pSource.is(DamageTypeTags.IS_FIRE)) {
            l = pLevel * 2;
        } else if (type == ProtectionEnchantment.Type.FALL && pSource.is(DamageTypeTags.IS_FALL)) {
            l = pLevel * 3;
        } else if (type == ProtectionEnchantment.Type.EXPLOSION && pSource.is(DamageTypeTags.IS_EXPLOSION)) {
            l = pLevel * 2;
        } else {
            l = type == ProtectionEnchantment.Type.PROJECTILE && pSource.is(DamageTypeTags.IS_PROJECTILE) ? pLevel * 2 : 0;
        }
        return l;
    }

    public static HarmEnchantment create(ResourceLocation resourceLocation) {
        if (resourceLocation.equals(HARM)){
            return new HarmEnchantment(Type.ALL);
        }
        if (resourceLocation.equals(HARM_FIRE)){
            return new HarmEnchantment(Type.FIRE);
        }
        if (resourceLocation.equals(HARM_FALL)){
            return new HarmEnchantment(Type.FALL);
        }
        if (resourceLocation.equals(HARM_EXPLOSION)){
            return new HarmEnchantment(Type.EXPLOSION);
        }
        if (resourceLocation.equals(HARM_PROJECTILE)){
            return new HarmEnchantment(Type.PROJECTILE);
        }
        throw new IllegalArgumentException("No harm enchantment like this");
    }
}
