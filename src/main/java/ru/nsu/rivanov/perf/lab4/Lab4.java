package ru.nsu.rivanov.perf.lab4;

import javassist.tools.rmi.Proxy;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Method;

public class Lab4 {

    static class Calculator {
        public int sum(int x, int y) {
            return x + y;
        }
    }

    static Calculator createCalculator() throws Exception {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(Calculator.class);
        factory.setFilter(method -> "sum".equals(method.getName()));
        MethodHandler methodHandler = (self, method, proceed, arguments) -> (Integer) ((int) proceed.invoke(self, arguments) + 1);
        Class factoryClass = factory.createClass();
        Object instance = factoryClass.newInstance();
        ((ProxyObject)instance).setHandler(methodHandler);
        return (Calculator) instance;
    }

    public static void main(String[] args) throws Exception {
        Calculator cal = createCalculator();
        System.out.println("2 + 2 = " + cal.sum(2, 2));
    }
}