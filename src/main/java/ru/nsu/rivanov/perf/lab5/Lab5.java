package ru.nsu.rivanov.perf.lab5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab5 {
    static <T> Object setLogging(final T obj) {

        InvocationHandler loggingHandler = (proxy, method, args) -> {
            List<Class<?>> interfaces = Arrays.asList(obj.getClass().getInterfaces());
            String targetName = method.getName();
            for (Class<?> anInterface : interfaces) {
                Arrays.stream(anInterface.getMethods())
                        .map(Method::getName)
                        .filter(targetName::contains)
                        .findAny()
                        .ifPresent(methodName -> System.out.println("Calling " + methodName + "#" + anInterface.getCanonicalName()));
            }
            Object result = method.invoke(obj, args);
            System.out.println("RETURN VAL: " + result);
            return result;
        };


        Object instance = Proxy.newProxyInstance(Lab5.class.getClassLoader(),
                obj.getClass().getInterfaces(),
                loggingHandler
        );
        return instance;
    }

    public static class A  implements B {
        @Override
        public void foo() {
            System.out.println("doing some work");
        }
    }

    public static interface B {
        void foo();
    }

    public static void main(String[] args) {
        A a = new A();
        B a1 = (B) setLogging(a);
        a1.foo();
    }
}
