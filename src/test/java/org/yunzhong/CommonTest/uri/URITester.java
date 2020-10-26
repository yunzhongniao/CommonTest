package org.yunzhong.CommonTest.uri;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class URITester {

    @Test
    public void testURI() {
        String uriPath = "http://yunzhong@yunzhong.com:8080/yunzhong/eat?when=afternoon#meat";
        URI uri = URI.create(uriPath);
        assertEquals("http", uri.getScheme());
        assertEquals("yunzhong.com", uri.getHost());
        assertEquals("/yunzhong/eat", uri.getPath());
        assertEquals("yunzhong", uri.getUserInfo());
        System.out.println("Authority = " + uri.getAuthority());
        System.out.println("Fragment = " + uri.getFragment());
        System.out.println("Host = " + uri.getHost());
        System.out.println("Path = " + uri.getPath());
        System.out.println("Port = " + uri.getPort());
        System.out.println("Query = " + uri.getQuery());
        System.out.println("Scheme = " + uri.getScheme());
        System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart());
        System.out.println("User Info = " + uri.getUserInfo());
        System.out.println("URI is absolute: " + uri.isAbsolute());
        System.out.println("URI is opaque: " + uri.isOpaque());
    }

    @Test
    public void testFileURI() {
        String uriPath = "file:///d/workroom";
        URI uri = URI.create(uriPath);
        assertEquals("file", uri.getScheme());
        assertEquals("/d/workroom", uri.getPath());
        System.out.println("Authority = " + uri.getAuthority());
        System.out.println("Fragment = " + uri.getFragment());
        System.out.println("Host = " + uri.getHost());
        System.out.println("Path = " + uri.getPath());
        System.out.println("Port = " + uri.getPort());
        System.out.println("Query = " + uri.getQuery());
        System.out.println("Scheme = " + uri.getScheme());
        System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart());
        System.out.println("User Info = " + uri.getUserInfo());
        System.out.println("URI is absolute: " + uri.isAbsolute());
        System.out.println("URI is opaque: " + uri.isOpaque());
    }

    @Test
    public void testWithPath() {
        Path path = Paths.get("/home/yunzhong", "temp", "replaceTable2.docx");
        URI uri = path.toUri();
        assertEquals("file", uri.getScheme());
        assertEquals("/home/yunzhong/temp/replaceTable2.docx", uri.getPath());
        System.out.println("Authority = " + uri.getAuthority());
        System.out.println("Fragment = " + uri.getFragment());
        System.out.println("Host = " + uri.getHost());
        System.out.println("Path = " + uri.getPath());
        System.out.println("Port = " + uri.getPort());
        System.out.println("Query = " + uri.getQuery());
        System.out.println("Scheme = " + uri.getScheme());
        System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart());
        System.out.println("User Info = " + uri.getUserInfo());
        System.out.println("URI is absolute: " + uri.isAbsolute());
        System.out.println("URI is opaque: " + uri.isOpaque());
    }

}
