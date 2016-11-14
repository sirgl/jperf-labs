package ru.nsu.rivanov.perf.lab6_1;

import javassist.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import ru.nsu.rivanov.perf.lab4.Lab4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class Lab6_1 {
    public static <T> Class<T> addSecurityMessage(Class<T> clazz, String securityMessage) throws IllegalAccessException, InstantiationException, NotFoundException, CannotCompileException {
        CtClass ctClass = ClassPool.getDefault().get(clazz.getCanonicalName());
        CtMethod newMethod = CtNewMethod.make("public String getSecurityMessage(){return \"" + securityMessage + "\";}", ctClass);
        ctClass.setName(clazz.getName() + "proxy");
        ctClass.addMethod(newMethod);
        ctClass.setSuperclass(ClassPool.getDefault().get(clazz.getCanonicalName()));
        return (Class<T>)ctClass.toClass();
    }

    static class A {
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NotFoundException, CannotCompileException, InvocationTargetException {
        Class<Lab6_1> aClass = addSecurityMessage(Lab6_1.class, "security message");
        Lab6_1 lab6_1 = aClass.newInstance();
        for (Method method : lab6_1.getClass().getMethods()) {
            if(method.getName().equals("getSecurityMessage")){
                System.out.println(method.invoke(lab6_1));
            }
        }


    }
}
