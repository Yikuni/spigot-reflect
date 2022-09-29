package com.yikuni.mc.reflect.loader;

import com.yikuni.mc.reflect.context.ApplicationContext;
import com.yikuni.mc.reflect.key.strategy.StringReplaceStrategy;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractLoader {
    protected JavaPlugin plugin;
    protected AbstractLoader next;    // 责任链模式
    protected Class<? extends Annotation> typeAnnotation;    // 标明这个注解的类
    protected Class<? extends Annotation> methodAnnotation;   // 标明有哪个注解的方法需要被装载
    protected Set<Class<?>> classSet = new HashSet<>();   // 需要被装载的类

    public AbstractLoader() {
    }


    /**
     * 解析这个类由哪个装载器装载, 仅限最开头的加载器使用
     * @param c 类
     */
    public final void resolve(Class<?> c){
        try {
            if (add(c)) return;
            if (next != null){
                next.resolve(c);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载列表中的每个类中的每个方法
     */
    public void loadClass(){
        classSet.forEach(c ->{
            try {
                Object o = loadClass(c);
                if(o != null){
                    ApplicationContext.addContext(o.getClass(), o);
                }
                if (methodAnnotation != null){
                    // 如果需要loadMethod
                    Method[] methods = c.getMethods();
                    for (Method m: methods){
                        if (m.getAnnotation(methodAnnotation) != null){
                            // 如果有需要装载的, 调用loadMethod
                            loadMethod(m);
                        }
                    }
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
        if (next != null){
            next.loadClass();
        }
    }

    abstract Object loadClass(Class<?> c) throws ReflectiveOperationException;


    protected abstract void loadMethod(Method method);

    /**
     * 增加要装载的类
     * @param c 需要装载的类
     * @return true: 由这个loader装载; false: 这个loader无法装载
     * @throws ReflectiveOperationException 反射异常
     */
    protected boolean add(Class<?> c) throws ReflectiveOperationException {
         Annotation annotation = c.getAnnotation(typeAnnotation);
        if (annotation != null){
            // 如果是这个装载器负责的类, 则装载
            classSet.add(c);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删去要装载的类
     * @param c 要remove的类
     */
    public void remove(Class<?> c){
        classSet.remove(c);
    }

    public void setReplaceStrategy(StringReplaceStrategy strategy){
        // do Nothing
    }

    public AbstractLoader getNext() {
        return next;
    }

    public void setNext(AbstractLoader next) {
        this.next = next;
    }

    public void setTypeAnnotation(Class<? extends Annotation> typeAnnotation) {
        this.typeAnnotation = typeAnnotation;
    }

    public void setMethodAnnotation(Class<? extends Annotation> methodAnnotation) {
        this.methodAnnotation = methodAnnotation;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }
}
