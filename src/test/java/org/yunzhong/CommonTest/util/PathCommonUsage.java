package org.yunzhong.CommonTest.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class PathCommonUsage {

    @Test
    public void testAppend() {
        System.out.println(Paths.get("D:/temp", "hello/world", "files").toString());
        System.out.println(Paths.get("D:/temp", "hello\\world", "files").toString());
        System.out.println(Paths.get("D:/temp", "hello", "world", "files").toString());
        
        Path path = Paths.get("D:/temp");
        Path resolve = path.resolve("hello").resolve("world").resolve("files");
        System.out.println(resolve.toString());
    }
}
