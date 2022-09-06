package com.yikuni.mc.reflect.loader;

public abstract class CheckLoader extends AbstractLoader {
    /**
     * Check the class to be load is valid or not
     * @param c class to be load
     * @param interface_    interface to be implemented or abstract class to be extended
     * @return  true if the class is invalid
     */
    protected boolean checkClass(Class<?> c, Class<?> interface_){
        return !interface_.isAssignableFrom(c);
    }
}
