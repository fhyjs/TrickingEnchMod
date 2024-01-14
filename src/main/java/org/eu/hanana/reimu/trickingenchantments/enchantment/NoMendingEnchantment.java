package org.eu.hanana.reimu.trickingenchantments.enchantment;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.common.Utils;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;

import java.util.Map;

public class NoMendingEnchantment extends MendingEnchantment implements ITrickingEnchantment{
    public NoMendingEnchantment() {
        super(Rarity.VERY_RARE, EquipmentSlot.values());
    }
    public static int breakPlayerItems(Player pPlayer, int pRepairAmount, ExperienceOrb orb) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(EnchantmentRegistryHandle.NoMending.get(), pPlayer, ItemStack::isDamageableItem);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int i = (int) (pRepairAmount * itemstack.getXpRepairRatio());
            itemstack.setDamageValue(itemstack.getDamageValue() + i);
            if (itemstack.getDamageValue()>itemstack.getMaxDamage()){
                orb.level().playSound(null,orb, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,70,1);
                itemstack.shrink(1);
                itemstack.setDamageValue(0);
            }
            int j = pRepairAmount - i/2;
            return j > 0 ? breakPlayerItems(pPlayer, j,orb) : 0;
        } else {
            return pRepairAmount;
        }
    }
}
