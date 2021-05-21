package com.simpledev.tools.javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;

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
        // new JavaParserSample().start();
        // new JavaParserSample().test2();
        // new JavaParserSample().test3();
        // new JavaParserSample().test4();
        // new JavaParserSample().test5();
        // new JavaParserSample().test6();
        // new JavaParserSample().test7();
        new JavaParserSample().test8();
    }

    /**
     * 打印方法名
     * @throws IOException
     */
    public void start() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\com.simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        MethodNamePrinter mp = new MethodNamePrinter();
        mp.visit(root, null);
    }

    /**
     * 收集方法名
     * @throws IOException
     */
    public void test2() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\com.simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        List<String> names = new ArrayList<>();
        MethodNameCollector collector = new MethodNameCollector();
        collector.visit(root, names);
        names.stream().map(name -> "name:" + name).forEach(System.out::println);
    }

    /**
     * 定义一个final变量
     */
    private static final int FLAG = 8;

    /**
     * 定义一个普通变量
     */
    private int num = 900_000_000; //啊

    /**
     * 改变变量值
     * @throws IOException
     */
    public void test3() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\com.simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        IntegerModifier modifier = new IntegerModifier();
        modifier.visit(root, null);
        // 将900_000_000 变成了 9_0000_0000;
        System.out.println(root.toString());
    }

    /**
     * 获取全部注释
     * @throws IOException
     */
    public void test4() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\com.simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        root.getAllComments().forEach(comment -> {
            System.out.println(comment.getContent());
        });
    }

    /**
     * 打印一些注释
     * @throws IOException
     */
    public void test5() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\com.simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));
        CommentVisitor visitor = new CommentVisitor();
        visitor.visit(root, null);
    }

    /**
     * 打印一个类
     */
    public void test6() {
        ClassOrInterfaceDeclaration clz = new ClassOrInterfaceDeclaration();
        clz.setJavadocComment("一个简单的测试类");
        clz.setName("Test");
        clz.addField(int.class, "id", Modifier.Keyword.PRIVATE);
        clz.addMethod("fun", Modifier.Keyword.PRIVATE);
        System.out.println(clz);
    }

    /**
     * 使用配置打印类
     */
    public void test7() {
        ClassOrInterfaceDeclaration clz = new ClassOrInterfaceDeclaration();
        clz.setJavadocComment("一个简单的测试类");
        clz.setName("Test");
        clz.addField(int.class, "id", Modifier.Keyword.PRIVATE);
        clz.addMethod("fun", Modifier.Keyword.PRIVATE);
        PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
        configuration.setPrintComments(false);
        configuration.setIndentSize(8); //正常应该是4，这里把其改成8
        PrettyPrinter prettyPrinter = new PrettyPrinter(configuration);
        System.out.println(prettyPrinter.print(clz));
    }

    /**
     * 得到变量类型
     * @throws IOException
     */
    public void test8() throws IOException {
        String path = "C:\\Users\\admin\\IdeaProjects\\SimpleDev_2\\Tools\\src\\main\\java\\com\\com.simpledev\\tools\\javaparser\\JavaParserSample.java";
        CompilationUnit root = StaticJavaParser.parse(new File(path));

        TypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver javaSymbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(javaSymbolSolver);

        root.findAll(AssignExpr.class).forEach(assignExpr -> {
            ResolvedType resolvedType = assignExpr.calculateResolvedType();
            System.out.println(assignExpr + " is " + resolvedType);
        });
    }
}
