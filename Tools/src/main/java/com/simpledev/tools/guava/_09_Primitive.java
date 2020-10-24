package com.simpledev.tools.guava;

import com.google.common.primitives.Ints;

public class _09_Primitive {
    public static void main(String ... args) {
        // 提供了一些原生用法支持,以ints为例子
        System.out.println(Ints.join(",", 1,2,3,4));
        int[] arr = new int[] {1, 2, 3};
        Ints.reverse(arr);
        Ints.contains(arr, 5);
        Ints.max(arr);
    }
}
