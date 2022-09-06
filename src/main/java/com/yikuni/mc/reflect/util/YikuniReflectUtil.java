package com.yikuni.mc.reflect.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class YikuniReflectUtil {
    public static Set<Class<?>> getClasses(String pack, Boolean recursive){
        Set<Class<?>> classes = new LinkedHashSet<>();
        String packageName = pack;
        String packDirName = pack.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = YikuniReflectUtil.class.getClassLoader().getResources(packDirName);
            while (dirs.hasMoreElements()){
                // 获取下一个要扫描的类
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)){
                    // 如果是file类型的扫描
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }else  if("jar".equals(protocol)){
                    // 如果是jar类型的扫描
                    JarFile jar;
                    jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()){
                        // 获取下一个要扫描的类
                        JarEntry jarEntry = entries.nextElement();
                        String name = jarEntry.getName();
                        if (name.startsWith("/")){
                            name = name.substring(1);
                        }
                        if (name.startsWith(packDirName)){
                            int idx = name.lastIndexOf('/');
                            if (idx != -1){
                                // 如果以/结尾则是一个包
                                packageName = name.substring(0, idx).replace('/', '.'   );
                            }
                            if (idx != -1 || recursive){
                                if (name.endsWith(".class") && !jarEntry.isDirectory()){
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    try {
                                        classes.add(Class.forName(packageName + "." +className));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes){
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || file.getName().endsWith(".class"));

        assert dirFiles != null;
        for(File file: dirFiles){
            if (file.isDirectory()){
                findAndAddClassesInPackageByFile(packageName + '.' + file.getName(), file.getAbsolutePath(), recursive, classes);
            }else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
