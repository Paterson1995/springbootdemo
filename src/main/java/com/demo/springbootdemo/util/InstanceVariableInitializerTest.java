package com.demo.springbootdemo.util;

import org.springframework.beans.factory.config.BeanDefinition;

public class InstanceVariableInitializerTest {

    private int j = getI();
    private int i = 1;

    public InstanceVariableInitializerTest() {
        i = 2;
    }

    private int getI() {
        return i;
    }

    public static void main(String[] args) {
        InstanceVariableInitializerTest ii = new InstanceVariableInitializerTest();
        System.out.println(ii.j);
    }

}
