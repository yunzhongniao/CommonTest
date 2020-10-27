package org.yunzhong.CommonTest.util.file;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FileSystemUtilTest {
	private static final Logger log = LoggerFactory.getLogger(FileSystemUtilTest.class);

	@Test
	void testPrintFileTree() throws IOException, URISyntaxException {
		URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		FileSystemUtil.printFileTree(path.toString());
	}

	/**
	 * @throws Exception
	 */
	@Test
	void testcreateDirectory() throws Exception {
		URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		path = Paths.get(path.toString(), "temp");
		FileSystemUtil.createDirectory(path.toString(), null);
	}

	/**
	 * @throws Exception
	 */
	@Test
	void testcreateDirectories() throws Exception {
		URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		path = Paths.get(path.toString(), "temp", "create", "test");
		FileSystemUtil.createDirectories(path.toString(), null);
		assertTrue(Files.exists(path));
	}

}
