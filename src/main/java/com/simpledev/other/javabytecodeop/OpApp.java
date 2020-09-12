package com.simpledev.other.javabytecodeop;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OpApp {

    public static void main(String ... args) throws Exception {
        System.out.println("---start---");
        new OpApp().opTest();
        System.out.println("---end----");
    }

    private void opTest() throws Exception {
        //addClassAnnotation();
        //addFieldAnnotation();
        //removeMethodAnnotation();
        replaceMethodAnnotation();
    }

    /**
     * 增加类注解
     */
    private void addClassAnnotation() throws Exception {
        System.out.println("--增加一个类注解");
        ClassPool cp = ClassPool.getDefault();

        ClassClassPath classPath = new ClassClassPath(this.getClass());
        cp.insertClassPath(classPath);

        String pkgName = "com.simpledev.other.javabytecodeop";
        CtClass cc = cp.get("com.simpledev.other.javabytecodeop.Target");
        cp.makePackage(cp.getClassLoader(), pkgName);

        ClassFile cfile = cc.getClassFile();
        ConstPool cpool = cfile.getConstPool();

        AnnotationsAttribute attr =
                new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
        Annotation annot = new Annotation("java.lang.Deprecated", cpool);
        attr.addAnnotation(annot);
        cfile.addAttribute(attr);

        Class<?> c = cc.toClass();

        // 测试是否增加注解了
        Class<?> clz = Target.class;
        for (java.lang.annotation.Annotation annotation : clz.getAnnotations()) {
            System.out.println("含有类注解：" + annotation);
        }
    }

    /**
     * 对某些字段增加注解
     */
    private void addFieldAnnotation() throws Exception {
        System.out.println("--增加一个字段注解");
        ClassPool cp = ClassPool.getDefault();

        ClassClassPath classPath = new ClassClassPath(this.getClass());
        cp.insertClassPath(classPath);

        String pkgName = "com.simpledev.other.javabytecodeop";
        CtClass cc = cp.get("com.simpledev.other.javabytecodeop.Target");
        cp.makePackage(cp.getClassLoader(), pkgName);

        ClassFile cfile = cc.getClassFile();
        ConstPool cpool = cfile.getConstPool();

        AnnotationsAttribute attr =
                new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
        Annotation annot = new Annotation("javax.persistence.Transient", cpool);
        attr.addAnnotation(annot);

        for (CtField field : cc.getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) {
                field.getFieldInfo().addAttribute(attr);
            }
        }

        Class<?> c = cc.toClass();

        // 测试是否增加注解了
        Class<?> clz = Target.class;
        for (Field field : clz.getDeclaredFields()) {
            for (java.lang.annotation.Annotation annotation : field.getAnnotations()) {
                System.out.println("含有字段注解："  + field + " " + annotation);
            }
        }
    }

    /**
     * 去掉方法注解
     */
    private void removeMethodAnnotation() throws Exception {
        System.out.println("--删除一个方法注解");
        ClassPool cp = ClassPool.getDefault();

        ClassClassPath classPath = new ClassClassPath(this.getClass());
        cp.insertClassPath(classPath);

        String pkgName = "com.simpledev.other.javabytecodeop";
        CtClass cc = cp.get("com.simpledev.other.javabytecodeop.Target");
        cp.makePackage(cp.getClassLoader(), pkgName);

        ClassFile cfile = cc.getClassFile();
        ConstPool cpool = cfile.getConstPool();

        AnnotationsAttribute attr =
                new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);

        CtMethod method = cc.getDeclaredMethod("getId");
        method.getMethodInfo().addAttribute(attr);

        Class<?> c = cc.toClass();

        // 测试是否增加注解了
        Class<?> clz = Target.class;
        for (Method method1 : clz.getDeclaredMethods()) {
            for (java.lang.annotation.Annotation annotation : method1.getAnnotations()) {
                System.out.println("含有方法注解："  + method1 + " " + annotation);
            }
        }
    }

    /**
     * 把@Id 换成@Depressed, 其他注解不变
     */
    private void replaceMethodAnnotation() throws Exception {
        System.out.println("--替换一个方法注解");
        ClassPool cp = ClassPool.getDefault();

        ClassClassPath classPath = new ClassClassPath(this.getClass());
        cp.insertClassPath(classPath);

        String pkgName = "com.simpledev.other.javabytecodeop";
        CtClass cc = cp.get("com.simpledev.other.javabytecodeop.Target");
        cp.makePackage(cp.getClassLoader(), pkgName);

        ClassFile cfile = cc.getClassFile();
        ConstPool cpool = cfile.getConstPool();

        AnnotationsAttribute attr =
                new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);

        CtMethod method = cc.getDeclaredMethod("setId");
        List list = new ArrayList();
        for(Object obj : method.getAnnotations()) {
            if (!obj.toString().contains(Id.class.getName())) {
                list.add(obj);
            }
        }
        for (Object o : list) {
            Annotation annot = new Annotation(o.toString().replace("@", ""), cpool);
            attr.addAnnotation(annot);
        }
        Annotation annot = new Annotation("java.lang.Deprecated", cpool);
        attr.addAnnotation(annot);
        method.getMethodInfo().addAttribute(attr);

        Class<?> c = cc.toClass();

        // 测试是否增加注解了
        Class<?> clz = Target.class;
        for (Method method1 : clz.getDeclaredMethods()) {
            for (java.lang.annotation.Annotation annotation : method1.getAnnotations()) {
                System.out.println("含有方法注解："  + method1 + " " + annotation);
            }
        }
    }
}
