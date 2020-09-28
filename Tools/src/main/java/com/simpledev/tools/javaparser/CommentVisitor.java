package com.simpledev.tools.javaparser;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CommentVisitor extends VoidVisitorAdapter {
    @Override
    public void visit(PackageDeclaration n, Object arg) {
        System.out.println("包注释：" + n.getComment());
        super.visit(n, arg);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        System.out.println("类注释：" + n.getComment());
        super.visit(n, arg);
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        System.out.println("字段注释：" + n.getComment());
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        System.out.println("方法注释：" + n.getComment());
        super.visit(n, arg);
    }
}
