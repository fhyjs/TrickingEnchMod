package org.eu.hanana.reimu.trickingenchantments.mixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.eu.hanana.reimu.trickingenchantments.enchantment.BreakingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackBreakingMixin{
    private ItemStack stack = (ItemStack) (Object) this;
    @Inject(at = @At("HEAD"), method = "hurt")
    public void hurt(int pAmount, RandomSource pRandom, ServerPlayer pUser, CallbackInfoReturnable<Boolean> cir) {
        if (BreakingEnchantment.shouldDoubleDurabilityDrop(stack, EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegistryHandle.Breaking.get(), stack),pRandom)) {
            if(pUser!=null){
                pUser.level().playSound(null,pUser, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,70,1);
            }
            int l = stack.getDamageValue() + pAmount*EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegistryHandle.Breaking.get(), stack);
            stack.setDamageValue(l);
            if (l >= stack.getMaxDamage()) {
                cir.setReturnValue(Boolean.TRUE);
            }
        }
    }
}
