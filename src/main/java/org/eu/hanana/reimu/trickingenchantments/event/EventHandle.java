package org.eu.hanana.reimu.trickingenchantments.event;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.eu.hanana.reimu.trickingenchantments.enchantment.SoulSlowEnchantment;
import org.eu.hanana.reimu.trickingenchantments.registry.EnchantmentRegistryHandle;

import java.util.HashMap;
import java.util.Map;

public class EventHandle {
    private final Map<Entity, BlockPos> entityBlockPos = new HashMap<>();
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
    public void onLivingBlockChanged(LivingEntity entity){
        if (!entity.level().getBlockState(entity.getOnPosLegacy()).isAir() || entity.isFallFlying()) {
            SoulSlowEnchantment.removeSoulSlow(entity);
        }
        if (entity.getFeetBlockState().is(BlockTags.SOUL_SPEED_BLOCKS)) {
            SoulSlowEnchantment.addSoulSlow(entity);
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
