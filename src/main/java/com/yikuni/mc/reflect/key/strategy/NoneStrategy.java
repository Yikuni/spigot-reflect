package com.yikuni.mc.reflect.key.strategy;

/**
 * 不进行替换
 */
public class NoneStrategy implements StringReplaceStrategy{
    @Override
    public String replace(String s) {
        return s;
    }
}
