package org.yunzhong.CommonTest.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.core.io.ClassPathResource;
import org.yunzhong.CommonTest.util.stream.InputStreamToString;

public class JarFileUtil {

    public static void printClassloaderPath(Class<?> clazz) {
        System.out.println(clazz.getClassLoader().getResource(""));
        System.out.println(clazz.getClassLoader().getResource("/"));
    }

    public static void printClassPath(Class<?> clazz) {
        System.out.println(clazz.getResource(""));
        System.out.println(clazz.getResource("/"));
    }

    public static void printFile(String path, Class<?> clazz) throws IOException {
        ClassPathResource res = new ClassPathResource(path, clazz);
        InputStream inputStream = res.getInputStream();
        System.out.println("resoure path:" + res.getPath());
        System.out.println(InputStreamToString.bufferReaderConvert(inputStream, Charset.forName("utf-8")));
    }

    public static void printFile(String path) throws IOException {
        ClassPathResource res = new ClassPathResource(path);
        InputStream inputStream = res.getInputStream();
        System.out.println("resoure path:" + res.getPath());
        System.out.println(InputStreamToString.commonIOConvert(inputStream, Charset.forName("utf-8")));
    }
}
