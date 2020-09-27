package com.simpledev.tools.javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试代码
 */

public class JavaParserSample {
    /**
     * main 方法
     * @param args
     */
    public static void main(String ... args) throws IOException {
        //new JavaParserSample().start();
        new JavaParserSample().test2();
    }

    public void start() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        MethodNamePrinter mp = new MethodNamePrinter();
        mp.visit(root, null);
    }

    public void test2() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        List<String> names = new ArrayList<>();
        MethodNameCollector collector = new MethodNameCollector();
        collector.visit(root, names);
        names.stream().map(name -> "name:" + name).forEach(System.out::println);
    }
}
