package com.simpledev.tools.guava;

import com.google.common.math.IntMath;

import java.math.BigInteger;
import java.math.RoundingMode;

public class _04_Math {

    public static void main(String ... args) {
        // 主要是包括 IntMath, LongMath, DoubleMath, and BigIntegerMath4个部分
        // 部分内容可能和jdk重叠，，例如下面的
        int v = BigInteger.valueOf(2).gcd(BigInteger.TEN).intValue();
        System.out.println(v);

        // 使用IntMath
        assert v == IntMath.gcd(2, 10);

        // log方法
        System.out.println(IntMath.log2(9, RoundingMode.CEILING)); // 值应该为4

        // 检查溢出
        int add = IntMath.checkedAdd(Integer.MAX_VALUE, 9); // 溢出则抛异常
    }
}
