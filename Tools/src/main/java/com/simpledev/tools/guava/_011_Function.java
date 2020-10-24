package com.simpledev.tools.guava;

public class _011_Function {

    public static void main(String ... args) {
        // 主要分4种
        // 消费型Consumer 传入1个参数，无返回值 实现void accept(T t)
        // 生产型Supplier 无传入参数, 有返回值 实现 T get()
        // 条件型Predicate 传入1个参数，发回true/false 实现boolean test(T t)
        // 功能性Function 传入1个参数，返回1个值  实现 R apply(T t) 方法
    }
}
