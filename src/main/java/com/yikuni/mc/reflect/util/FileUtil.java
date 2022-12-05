package com.yikuni.mc.reflect.util;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileUtil {
    public static byte[] getFileBytes(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
            try {
            fileInputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fileInputStream.read(b)) != -1) {
                byteArrayOutputStream.write(b, 0, n);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String readFileToString(InputStream in){
        BufferedInputStream bin = null;
        Charset charset = StandardCharsets.UTF_8;
        try {
            bin = new BufferedInputStream(in);
            byte[] bytes = new byte[1024];
            StringBuilder builder = new StringBuilder();
            while (bin.read(bytes, 0, bytes.length) != -1){
                builder.append(Arrays.toString(bytes));
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bin != null){
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String readFileToString(File file){
        if(file.exists() && !file.isDirectory()){
            FileInputStream fileInputStream = null;
            Charset charset = StandardCharsets.UTF_8;
            try {
                fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                fileInputStream.read(bytes);
                String result = new String(bytes, charset);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static void write(String path, String content) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        write(file, content);
    }
    public static void write(File file, String content) {
        FileWriter out = null;
        try {
            out = new FileWriter(file);
            out.write(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void deleteFileIfExist(String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    public static void clearFile(String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(File file){
        if (file.exists()){
            if (file.isDirectory()){
                // 如果是文件夹
                File[] files = file.listFiles();
                if (files.length == 0){
                    // 如果是空文件夹
                }else {
                    for (File child: files){
                        deleteFile(child);
                    }
                }
                file.delete();
            }else {
                file.delete();
            }
        }
    }
}
