package org.eu.hanana.reimu.trickingenchantments.enchantment;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.SoulSpeedEnchantment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;

import static org.eu.hanana.reimu.trickingenchantments.common.Datas.SPEED_MODIFIER_SOUL_SLOW_UUID;

public class BreakingEnchantment extends DigDurabilityEnchantment implements ITrickingEnchantment{
    public BreakingEnchantment() {
        super(Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
    }
    public static boolean shouldDoubleDurabilityDrop(ItemStack pStack, int pLevel, RandomSource pRandom) {
        if (pStack.getItem() instanceof ArmorItem && pRandom.nextFloat() < 0.6F) {
            return false;
        } else {
            return pRandom.nextInt(pLevel + 1) > 0;
        }
    }
}
