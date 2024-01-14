package org.eu.hanana.reimu.trickingenchantments.mixins;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.eu.hanana.reimu.trickingenchantments.enchantment.BreakingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.event.EventHandle;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ClientHooks.class)
public class ClientHookMixin {
    private ItemStack stack = (ItemStack) (Object) this;
    @Inject(at = @At("HEAD"), method = "onRenderTooltipPre")
    private static void onRenderTooltipPre(@NotNull ItemStack stack, GuiGraphics graphics, int x, int y, int screenWidth, int screenHeight, @NotNull List<ClientTooltipComponent> components, @NotNull Font fallbackFont, @NotNull ClientTooltipPositioner positioner, CallbackInfoReturnable<RenderTooltipEvent.Pre> cir){

    }
}
