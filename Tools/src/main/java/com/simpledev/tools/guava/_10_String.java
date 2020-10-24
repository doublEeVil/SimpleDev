package com.simpledev.tools.guava;

import com.google.common.base.*;

import java.util.Arrays;

public class _10_String {

    public static void main(String ... args) {
        // 1. Joiner
        Joiner joiner = Joiner.on(",").skipNulls();
        System.out.println(joiner.join("i", null, "am", "boy"));
        System.out.println(joiner.join(Arrays.asList(1, 2, 5)));

        // 2. Splitter
        System.out.println("---Splitter");
        String src = "yes, i do, and , ,i will";
        Splitter.on(",")
                .trimResults() // 不需要前后空格
                .omitEmptyStrings() // 不需要空字符串
                .split(src)
                .forEach(System.out::println);

        // 也可以按照固定长度分割
        System.out.println("---by length");
        Splitter.fixedLength(3).split(src).forEach(System.out::println);

        // 3. CharMatcher
        System.out.println("---CharMatcher");
        // 消除控制字符
        src = "lalala, my id is 223, and 14 year old, i am \t \n mai bao d \t xiao hangjia \r";
        System.out.println(CharMatcher.javaIsoControl().removeFrom(src));
        // 只保留数字
        System.out.println(CharMatcher.digit().retainFrom(src));

        // 4. 字符集
        src.getBytes(Charsets.UTF_8); // 不用再去处理字符集不支持的异常了

        // 5. 大小写处理
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, src));
    }
}
