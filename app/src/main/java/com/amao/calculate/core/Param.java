package com.amao.calculate.width.core;

public class Param {
    
    private String name;
    private Object value;

    public Param(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
