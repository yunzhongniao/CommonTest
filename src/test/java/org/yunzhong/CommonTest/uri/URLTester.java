package org.yunzhong.CommonTest.uri;

import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class URLTester {

    @Test
    public void testBasic() throws MalformedURLException {
        URL url = new URL("http://www.yunzhong.com:8080/index.html?language=cn#j2se");
        assertEquals("http",url.getProtocol());
        assertEquals("www.yunzhong.com", url.getHost());
        assertEquals(8080, url.getPort());
        assertEquals(80, url.getDefaultPort());
        assertEquals("/index.html", url.getPath());
        System.out.println("URL 为：" + url.toString());
        System.out.println("协议为：" + url.getProtocol());
        System.out.println("验证信息：" + url.getAuthority());
        System.out.println("文件名及请求参数：" + url.getFile());
        System.out.println("主机名：" + url.getHost());
        System.out.println("路径：" + url.getPath());
        System.out.println("端口：" + url.getPort());
        System.out.println("默认端口：" + url.getDefaultPort());
        System.out.println("请求参数：" + url.getQuery());
        System.out.println("定位位置：" + url.getRef());
    }
    
    @Test
    public void testHttps() throws MalformedURLException {
        URL url = new URL("https://www.yunzhong.com:8080/index.html?language=cn#j2se");
        assertEquals("https",url.getProtocol());
        assertEquals("www.yunzhong.com", url.getHost());
        assertEquals(8080, url.getPort());
        assertEquals(443, url.getDefaultPort());
        assertEquals("/index.html", url.getPath());
        System.out.println("URL 为：" + url.toString());
        System.out.println("协议为：" + url.getProtocol());
        System.out.println("验证信息：" + url.getAuthority());
        System.out.println("文件名及请求参数：" + url.getFile());
        System.out.println("主机名：" + url.getHost());
        System.out.println("路径：" + url.getPath());
        System.out.println("端口：" + url.getPort());
        System.out.println("默认端口：" + url.getDefaultPort());
        System.out.println("请求参数：" + url.getQuery());
        System.out.println("定位位置：" + url.getRef());
    }
}
