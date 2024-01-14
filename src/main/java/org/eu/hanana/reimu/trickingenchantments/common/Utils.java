package org.eu.hanana.reimu.trickingenchantments.common;

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
}
