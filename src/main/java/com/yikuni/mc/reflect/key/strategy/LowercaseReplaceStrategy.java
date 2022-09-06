package com.yikuni.mc.reflect.key.strategy;

import java.util.Locale;

public class LowercaseReplaceStrategy implements StringReplaceStrategy{
    @Override
    public String replace(String s) {
        return s.toLowerCase(Locale.ROOT);
    }
}
