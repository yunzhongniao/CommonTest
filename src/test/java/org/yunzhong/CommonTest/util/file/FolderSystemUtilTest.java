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

class FolderSystemUtilTest {
	private static final Logger log = LoggerFactory.getLogger(FolderSystemUtilTest.class);

	@Test
	void testPrintFileTree() throws IOException, URISyntaxException {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		FolderSystemUtil.printFileTree(path.toString());
	}

	/**
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	void testprintFileDirectory() throws IOException, URISyntaxException {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		FolderSystemUtil.printFileDirectory(path.toString());
	}

	/**
	 * @throws Exception
	 */
	@Test
	void testcreateDirectory() throws Exception {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		path = Paths.get(path.toString(), "temp");
		FolderSystemUtil.createDirectory(path.toString(), null);
	}

	/**
	 * @throws Exception
	 */
	@Test
	void testprintDirectory() throws Exception {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		FolderSystemUtil.printDirectory(path.toString(), null);
	}

	/**
	 * @throws Exception
	 */
	@Test
	void testcreateDirectories() throws Exception {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		path = Paths.get(path.toString(), "temp", "create", "test");
		FolderSystemUtil.createDirectories(path.toString(), null);
		assertTrue(Files.exists(path));
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testremoveDirectory() throws Exception {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		FolderSystemUtil.removeDirectory(path.toString());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testremoveDirectoryRecursive() throws Exception {
		URL resource = FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
		Path path = Paths.get(resource.toURI());
		FolderSystemUtil.removeDirectoryRecursive(path.toString());
	}

}
