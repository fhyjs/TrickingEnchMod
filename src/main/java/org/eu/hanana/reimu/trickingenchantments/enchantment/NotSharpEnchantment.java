package org.eu.hanana.reimu.trickingenchantments.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class NotSharpEnchantment extends Enchantment implements ITrickingEnchantment{
    public NotSharpEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND});
    }
    @Override
    public int getMaxLevel() {
        return 4;
    }
    @Override
    public float getDamageBonus(int pLevel, MobType pCreatureType) {
        return -1.0F - (float)Math.max(0, pLevel - 1)*1.3F ;
    }

}
