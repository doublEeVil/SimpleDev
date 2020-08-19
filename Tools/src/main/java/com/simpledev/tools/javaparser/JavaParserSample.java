package com.simpledev.tools.javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 测试代码
 */

public class JavaParserSample {

    public static void printComment(String javaFile) throws IOException {
        CompilationUnit unit = StaticJavaParser.parse(Paths.get(javaFile));
        Node node = unit.findRootNode();
        node.getComment().ifPresent(x->{
            System.out.println(x);
        });
    }

    public static void main(String ... args) throws IOException {
        printComment("C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\simpledev\\tools\\javaparser\\JavaParserSample.java");
    }
}
