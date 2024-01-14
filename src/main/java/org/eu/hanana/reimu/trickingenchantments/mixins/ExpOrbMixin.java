package org.eu.hanana.reimu.trickingenchantments.mixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.eu.hanana.reimu.trickingenchantments.enchantment.BreakingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.enchantment.NoMendingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrb.class)
public class ExpOrbMixin {
    private ExperienceOrb orb = (ExperienceOrb) (Object) this;
    @Inject(at = @At("HEAD"), method = "repairPlayerItems")
    public void hurt(Player pPlayer, int pRepairAmount, CallbackInfoReturnable<Integer> cir) {
        NoMendingEnchantment.breakPlayerItems(pPlayer,pRepairAmount,orb);
    }
}
