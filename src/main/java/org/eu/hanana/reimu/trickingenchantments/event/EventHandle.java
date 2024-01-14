package org.eu.hanana.reimu.trickingenchantments.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.apache.commons.lang3.mutable.MutableInt;
import org.eu.hanana.reimu.trickingenchantments.common.Utils;
import org.eu.hanana.reimu.trickingenchantments.enchantment.HarmEnchantment;
import org.eu.hanana.reimu.trickingenchantments.enchantment.ITrickingEnchantment;
import org.eu.hanana.reimu.trickingenchantments.enchantment.SoulSlowEnchantment;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHandle {
    private final Map<Entity, BlockPos> entityBlockPos = new HashMap<>();
    private final List<DamageSource> damageFires = new ArrayList<>();
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event){
        entityBlockPos.clear();
    }
    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event){
        entityBlockPos.put(event.getEntity(),event.getEntity().blockPosition());
    }
    @SubscribeEvent
    public void onEntityJoinLevel(EntityLeaveLevelEvent event){
        entityBlockPos.remove(event.getEntity());
    }
    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingTickEvent event){
        LivingEntity entity = event.getEntity();
        if (!entity.blockPosition().equals(entityBlockPos.get(entity))){
            entityBlockPos.put(entity,entity.blockPosition());
            onLivingBlockChanged(entity);
        }
        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandle.SOUL_SLOW.get(), entity)>0&&entity.getFeetBlockState().is(BlockTags.SOUL_SPEED_BLOCKS)){
            SoulSlowEnchantment.spawnSoulSpeedParticle(entity);
        }
    }
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        LivingEntity entity = event.getEntity();
        DamageSource pSource = event.getSource();
        if (!damageFires.contains(pSource)) {
            damageFires.add(pSource);
            HarmEnchantment.fire(entity, pSource, event.getAmount());
        }else {
            damageFires.remove(pSource);
        }

        }
    public void onLivingBlockChanged(LivingEntity entity){
        if (!entity.level().getBlockState(entity.getOnPosLegacy()).isAir() || entity.isFallFlying()) {
            SoulSlowEnchantment.removeSoulSlow(entity);
        }
        if (entity.getFeetBlockState().is(BlockTags.SOUL_SPEED_BLOCKS)) {
            SoulSlowEnchantment.addSoulSlow(entity);
        }
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void preRenderTooltips(RenderTooltipEvent.Pre event){
        List<ClientTooltipComponent> components = event.getComponents();
        ClientTooltipPositioner tooltipPositioner = event.getTooltipPositioner();
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onItemTooltip(ItemTooltipEvent event){
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof AbstractContainerScreen<?> containerScreen) {
            Slot slot;
            if ((slot=containerScreen.getSlotUnderMouse())!=null){
                ItemStack itemStack = slot.getItem();
                if (Utils.hasEnchantment(ITrickingEnchantment.class,itemStack)) {
                    try {
                        event.getToolTip().add(1,Component.translatable("tooltip.trickingenchantments.enchanted"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event){
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide && entity.onGround() && entity.fallDistance > 0.0F) {
            SoulSlowEnchantment.removeSoulSlow(entity);
            SoulSlowEnchantment.addSoulSlow(entity);
        }
    }
}
