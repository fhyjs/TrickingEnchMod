package org.eu.hanana.reimu.trickingenchantments.common;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantmentId;
import static net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantmentLevel;

public class Utils {
    // 计算平均值的方法
    public static double calculateAverage(double[] array) {
        double sum = 0;

        // 计算总和
        for (double num : array) {
            sum += num;
        }

        // 计算平均值
        double average = sum / array.length;

        return average;
    }
    public static void runIterationOnInventory(EnchantmentVisitor pVisitor, Iterable<ItemStack> pStacks) {
        for(ItemStack itemstack : pStacks) {
            runIterationOnItem(pVisitor, itemstack);
        }
    }
    public static void runIterationOnItem(EnchantmentVisitor pVisitor, ItemStack pStack) {
        if (!pStack.isEmpty()) {
            if (true) { // forge: redirect enchantment logic to allow non-NBT enchants
                for (Map.Entry<Enchantment, Integer> entry : pStack.getAllEnchantments().entrySet()) {
                    pVisitor.accept(entry.getKey(), entry.getValue());
                }
                return;
            }

            ListTag listtag = pStack.getEnchantmentTags();

            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                BuiltInRegistries.ENCHANTMENT
                        .getOptional(getEnchantmentId(compoundtag))
                        .ifPresent(p_182437_ -> pVisitor.accept(p_182437_, getEnchantmentLevel(compoundtag)));
            }
        }
    }
    public static boolean hasEnchantment(Class<?> enchantment,ItemStack itemStack){
        for (Enchantment enchantment1 : EnchantmentHelper.getEnchantments(itemStack).keySet()) {
            if (enchantment.isInstance(enchantment1)) return true;
        }
        return false;
    }
    @FunctionalInterface
    public interface EnchantmentVisitor {
        void accept(Enchantment pEnchantment, int pLevel);
    }
}
