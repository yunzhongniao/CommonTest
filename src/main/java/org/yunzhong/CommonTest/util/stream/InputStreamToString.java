package org.yunzhong.CommonTest.util.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import com.google.common.io.CharStreams;

/**
 * @author yunzhong
 *
 */
public class InputStreamToString {

    /**
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String bufferReaderConvert(InputStream inputStream, Charset charset) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 用流式编程接口减少代码
     * 
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String bufferReaderConvertStream(InputStream inputStream, Charset charset) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    /**
     * Scanner用于通过系统输入的信息读取。
     * 
     * <pre>
     * Scanner scan = new Scanner(System.in);
     * if (scan.hasNext()) {
     *     String str1 = scan.next();
     *     System.out.println("输入的数据为：" + str1);
     * }
     * scan.close();
     * </pre>
     * 
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String scannerConvert(InputStream inputStream, Charset charset) throws IOException {

        try (Scanner scanner = new Scanner(inputStream, charset.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    /**
     * 第三方依赖：
     * 
     * <pre>
     * <dependency>
     *      <groupId>commons-io</groupId>
     *      <artifactId>commons-io</artifactId>
     * </dependency>
     * </pre>
     * 
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String commonIOConvert(InputStream inputStream, Charset charset) throws IOException {
        return IOUtils.toString(inputStream, charset);
    }

    /**
     * 用google guava工具类
     * 
     * <pre>
     * <dependency>
     *      <groupId>com.google.guava</groupId>
     *      <artifactId>guava</artifactId>
     * </dependency>
     * </pre>
     * 
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String guavaConvert(InputStream inputStream, Charset charset) throws IOException {
        return CharStreams.toString(new InputStreamReader(inputStream, charset));
    }
}
