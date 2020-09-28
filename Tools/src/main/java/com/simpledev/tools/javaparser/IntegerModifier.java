package com.simpledev.tools.javaparser;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class IntegerModifier extends ModifierVisitor<Void> {
    @Override
    public Visitable visit(FieldDeclaration n, Void arg) {
        super.visit(n, arg);
        //
        n.getVariables().forEach( node -> {
            System.out.println("name:" + node.getName() + " type:" + node.getType() + " init:" + node.getInitializer() + " comment:" + node.getComment());
            node.getInitializer().ifPresent(val -> {
                if (val.isIntegerLiteralExpr()) {
                    if (val.asIntegerLiteralExpr().asNumber().intValue() == 900_000_000) {
                        node.setInitializer(new IntegerLiteralExpr("9_0000_0000"));
                    }
                }
            });
        });
        return n;
    }
}
