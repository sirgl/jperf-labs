package ru.nsu.rivanov.perf.lab5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Lab5 {
    static <T> T setLogging(final T obj) {

//        Class<?>[] interfaces = obj.getClass().getInterfaces();
        InvocationHandler loggingHandler = (proxy, method, args) -> {
            System.out.println("Calling " + obj + "#" + method.getName());
            Object result = method.invoke(proxy, args);
            System.out.println("RETURN VAL: " + result);
            return result;
        };


        T instance = (T) Proxy.newProxyInstance(Lab5.class.getClassLoader(),
                new Class[]{obj.getClass()},
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
        A a1 = setLogging(a);
        a1.foo();
    }
}
