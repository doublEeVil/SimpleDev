package com.simpledev.tools.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.MoreFiles;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class _07_IO {
    public static void main(String ... args) throws IOException {
        // 针对jdk7之前的版本，提供的工具会非常好用，jdk之后的版本，可以使用jdk自带的组件
        // 主要包括下面几个Files, MoreFiles, Resources, ByteSource, CharSource

        // 1. 一行一行读取文件
        String path = "D:\\_Data\\SimpleDev\\Tools\\src\\main\\java\\com\\simpledev\\tools\\guava\\_07_IO.java";
        Files.asCharSource(new File(path), Charset.defaultCharset())
                .readLines()
                .forEach(System.out::println);

        // 2. 计算文件hash值
        System.out.println("---calc hash");
        HashCode hashCode = Files.asByteSource(new File(path)).hash(Hashing.sha512());
        System.out.println("---hash:" + hashCode);

        // 3. 快速拷贝文件
        File dst = new File("F:\\e.data");
        Resources.asByteSource( new URL("https://www.baidu.com/index.html")).copyTo(Files.asByteSink(dst));

        // 4. 获取文件名（不需要扩展名）
        File src = new File(path);
        System.out.println("文件名：" + Files.getNameWithoutExtension(path));
        System.out.println("扩展名：" + Files.getFileExtension(path));

        // 5. 统计不同的单词出现的次数
        Multiset<String> wordOccurrences = HashMultiset.create(
                Splitter.on(CharMatcher.whitespace())
                .trimResults()
                .omitEmptyStrings()
                .split(Files.asCharSource(src, Charset.defaultCharset()).read())
        );
        System.out.println("---统计结果：");
        wordOccurrences.stream().forEach(s -> {
            System.out.println(s + " : " + wordOccurrences.count(s));
        });
    }
}
