package org.yunzhong.CommonTest.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
class InputStreamToStringTest {
    private FileInputStream inputStream = null;

    @BeforeEach
    public void beforeEach() throws FileNotFoundException {
        URL resource = InputStreamToStringTest.class.getClassLoader().getResource("");
        Path path = Paths.get(resource.getPath(), "stream-test.txt");
        System.out.println("open stream.");
        inputStream = new FileInputStream(path.toFile());

    }

    @AfterEach
    public void afterEach() throws IOException {
        if (inputStream != null) {
            System.out.println("close stream.");
            inputStream.close();
        }
    }

    @Test
    void testBufferReaderConvert() throws IOException {
        String bufferReaderConvert = InputStreamToString.bufferReaderConvert(this.inputStream,
                Charset.forName("UTF-8"));
        assertEquals("Hello World", bufferReaderConvert);
    }

    @Test
    void testBufferReaderConvertStream() throws IOException {
        String content = InputStreamToString.bufferReaderConvertStream(this.inputStream, Charset.forName("UTF-8"));
        assertEquals("Hello World", content);
    }

    @Test
    void testScannerConvert() throws IOException {
        String content = InputStreamToString.scannerConvert(this.inputStream, Charset.forName("UTF-8"));
        assertEquals("Hello World", content);
    }

    @Test
    void testCommonIOConvert() throws IOException {
        String content = InputStreamToString.commonIOConvert(this.inputStream, Charset.forName("UTF-8"));
        assertEquals("Hello World", content);
    }

    @Test
    void testGuavaConvert() throws IOException {
        String content = InputStreamToString.guavaConvert(this.inputStream, Charset.forName("UTF-8"));
        assertEquals("Hello World", content);
    }

}
