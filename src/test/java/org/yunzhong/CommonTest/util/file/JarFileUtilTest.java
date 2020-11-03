package org.yunzhong.CommonTest.util.file;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.web.WebApplicationInitializer;

class JarFileUtilTest {

    @Test
    void testprintClassloaderPath() {
        JarFileUtil.printClassloaderPath(WebApplicationInitializer.class);
    }

    @Test
    void testprintClassPath() {
        JarFileUtil.printClassPath(WebApplicationInitializer.class);
    }

    @Test
    void testPrintFile() throws IOException {
        //类路径下的文件
        JarFileUtil.printFile("package-info.class", WebApplicationInitializer.class);
        //classloader路径下的文件
        JarFileUtil.printFile("META-INF/license.txt");
        //classloader路径下的文件
        JarFileUtil.printFile("application.properties");
    }

}
