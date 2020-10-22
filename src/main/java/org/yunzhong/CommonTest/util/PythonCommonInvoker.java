package org.yunzhong.CommonTest.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yunzhong
 *
 */
public class PythonCommonInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonCommonInvoker.class);

    public static void invoke(String command, String... params) throws IOException, InterruptedException {
        String param1 = params[0];
        String param2 = params[1];
        Process process = Runtime.getRuntime()
                .exec(new String[] { "/usr/bin/python3", command, "--firstParam", param1, "--secondParam", param2 });
        // 5 等待执行结果
        process.waitFor();
        InputStream inputStream = process.getInputStream();
        LOGGER.info("invoke python log output:" + IOUtils.toString(inputStream, Charset.defaultCharset()));
        inputStream.close();
        InputStream errorStream = process.getErrorStream();
        LOGGER.error("exec python error:" + IOUtils.toString(errorStream, Charset.defaultCharset()));
        errorStream.close();
    }
}
