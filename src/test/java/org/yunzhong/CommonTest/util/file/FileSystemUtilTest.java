package org.yunzhong.CommonTest.util.file;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
class FileSystemUtilTest {

    @Test
    void testPrintSmallFile() throws Exception {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/second1/second1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.printSmallFile(path.toString());
    }

    @Test
    void testPrintSmallFileByLine() throws Exception {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/second1/second1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.printSmallFileByLine(path.toString());
    }

    @Test
    void testPrintFileByStream() throws Exception {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/second1/second1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.printFileByStream(path.toString());
    }

    /**
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    void testPrintAsync() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/second1/second1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.printAsync(path.toString());
    }

    /**
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    void testprintAsyncHandler() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/second1/second1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.printAsyncHandler(path.toString());
    }

    /**
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    void testwriteSmallFile() throws Exception {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/first1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.writeSmallFile(path.toString());

        FileSystemUtil.printSmallFile(path.toString());
    }
    
    @Test
    void testwriteFile() throws Exception {
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/first1.txt");
        Path path = Paths.get(resource.toURI());
        FileSystemUtil.writeFile(path.toString());

        FileSystemUtil.printSmallFile(path.toString());
    }
}
