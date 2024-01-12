package org.eu.hanana.reimu.trickingenchantments.enchantment;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.SoulSpeedEnchantment;
import net.minecraft.world.phys.Vec3;
import org.eu.hanana.reimu.trickingenchantments.common.Datas;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;

import static org.eu.hanana.reimu.trickingenchantments.common.Datas.SPEED_MODIFIER_SOUL_SLOW_UUID;

public class SoulSlowEnchantment extends SoulSpeedEnchantment {
    public SoulSlowEnchantment() {
        super(Rarity.VERY_RARE, EquipmentSlot.FEET);
    }
    public static void removeSoulSlow(LivingEntity entity) {
        AttributeInstance attributeinstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeinstance != null) {
            if (attributeinstance.getModifier(SPEED_MODIFIER_SOUL_SLOW_UUID) != null) {
                attributeinstance.removeModifier(SPEED_MODIFIER_SOUL_SLOW_UUID);
            }
        }
    }
    public static void spawnSoulSpeedParticle(LivingEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        entity.level()
                .addParticle(
                        ParticleTypes.SOUL,
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * (double)entity.getBbWidth(),
                        entity.getY() + 0.1,
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * (double)entity.getBbWidth(),
                        vec3.x * -0.2,
                        0.1,
                        vec3.z * -0.2
                );
        float f = entity.getRandom().nextFloat() * 0.4F + entity.getRandom().nextFloat() > 0.9F ? 0.6F : 0.0F;
        entity.playSound(SoundEvents.SLIME_BLOCK_STEP, f, 0.6F + entity.getRandom().nextFloat() * 0.4F);
    }
    public static void addSoulSlow(LivingEntity entity) {
        int soulSlowLv= EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandle.SOUL_SLOW.get(), entity);
        if (soulSlowLv>0){
            AttributeInstance attributeinstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeinstance == null) {
                return;
            }
            int i = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandle.SOUL_SLOW.get(), entity);
            try {
                 attributeinstance.addTransientModifier(
                        new AttributeModifier(
                                SPEED_MODIFIER_SOUL_SLOW_UUID, "Soul slow down", -0.03F * (1.0F + (float)i * 0.35F), AttributeModifier.Operation.ADDITION
                        )
                );
            }catch (Exception e){
                e.printStackTrace();
            }

            if (entity.getRandom().nextFloat() < 0.04F) {
                ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.FEET);
                itemstack.hurtAndBreak(1, entity, p_21301_ -> p_21301_.broadcastBreakEvent(EquipmentSlot.FEET));
            }
        }
    }
}
