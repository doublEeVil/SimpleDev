package com.simpledev.tools.javaparser;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
    @Override
    public void visit(MethodDeclaration n, List<String> arg) {
        super.visit(n, arg);
        arg.add(n.getNameAsString());
    }
}
