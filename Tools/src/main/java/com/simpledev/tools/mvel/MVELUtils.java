package com.simpledev.tools.mvel;

import org.mvel2.MVEL;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.util.HashMap;
import java.util.Map;

public class MVELUtils {

    /**
     * 表示可以是(A+B)/(Math.max(1,C)) 这样的简单的数学类型，但不可以使用{}这样的符号
     * @param expression
     * @param params
     * @return
     */
    public static double eval(String expression, Map<String, Double> params) {
        Object ret = MVEL.eval(expression, params);
        return Double.parseDouble(ret.toString());
    }

    public static void main(String ... args) {
        // 1. test math
        String expression = "(A+B)/Math.max(2,C)";
        Map map = new HashMap();
        map.put("A", 2);
        map.put("B", 3);
        map.put("C", 2.5);
        System.out.println(eval(expression, map));
    }
}
