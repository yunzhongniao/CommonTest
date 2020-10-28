package org.yunzhong.CommonTest.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author yunzhong
 *
 */
public class FolderSystemUtil {
	private static final Logger log = LoggerFactory.getLogger(FolderSystemUtil.class);

	/**
	 * print file in folder
	 * 
	 * @param rootPath
	 * @throws IOException
	 */
	public static void printFileTree(String rootPath) throws IOException {
		Path path = Paths.get(rootPath);
		if (Files.notExists(path)) {
			log.warn("there is no path {}", rootPath);
			return;
		}
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes arg1) throws IOException {
				System.out.println(path.toString());
				return FileVisitResult.CONTINUE;
			}

		});
		System.out.println("split--------------------------------------");
		Files.walkFileTree(path, new FileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes arg1) throws IOException {
				System.out.println(path.toString());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
				System.out.println("post visit directory:" + arg0.toString());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
				System.out.println("pre visit directory:" + arg0.toString());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}

		});
	}

	/**
	 * print file in folder
	 * 
	 * @param rootPath
	 * @throws IOException
	 */
	public static void printFileDirectory(String rootPath) throws IOException {
		Path path = Paths.get(rootPath);
		if (Files.notExists(path)) {
			log.warn("there is no path {}", rootPath);
			return;
		}
		File[] listFiles = path.toFile().listFiles();
		for (File file : listFiles) {
			System.out.println("file :" + file.getAbsolutePath());
		}
	}

	/**
	 * @param rootPath
	 * @param glob
	 * @throws IOException
	 */
	public static void printDirectory(String rootPath, String glob) throws IOException {
		Path path = Paths.get(rootPath);
		if (Files.notExists(path)) {
			log.warn("there is no path {}", rootPath);
			return;
		}
		DirectoryStream<Path> newDirectoryStream = null;
		try {
			if (glob != null) {
				newDirectoryStream = Files.newDirectoryStream(path, glob);
			} else {
				newDirectoryStream = Files.newDirectoryStream(path);
			}
			for (Path directory : newDirectoryStream) {
				System.out.println("path:" + directory.toString());
			}
		} finally {
			if (newDirectoryStream != null) {
				newDirectoryStream.close();
			}
		}
	}

	/**
	 * @param rootPath
	 * @param attr
	 * @throws Exception
	 */
	public static void createDirectory(String rootPath, String attr) throws Exception {
		Path createDirectory = null;
		if (StringUtils.isEmpty(attr)) {
			Path path = Paths.get(rootPath);
			createDirectory = Files.createDirectory(path);
		} else {
			Set<PosixFilePermission> attrSet = PosixFilePermissions.fromString(attr);
			FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixFilePermissions.asFileAttribute(attrSet);
			Path path = Paths.get(rootPath);
			createDirectory = Files.createDirectory(path, fileAttribute);
		}
		if (!Files.exists(createDirectory)) {
			throw new Exception();
		}
	}

	/**
	 * @param rootPath
	 * @param attr
	 * @throws Exception
	 */
	public static void createDirectories(String rootPath, String attr) throws Exception {
		Path createDirectory = null;
		if (StringUtils.isEmpty(attr)) {
			Path path = Paths.get(rootPath);
			createDirectory = Files.createDirectories(path);
		} else {
			Set<PosixFilePermission> attrSet = PosixFilePermissions.fromString(attr);
			FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixFilePermissions.asFileAttribute(attrSet);
			Path path = Paths.get(rootPath);
			createDirectory = Files.createDirectories(path, fileAttribute);
		}
		if (!Files.exists(createDirectory)) {
			throw new Exception();
		}
	}

	/**
	 * @param rootPath
	 * @throws IOException
	 */
	public static void removeDirectory(String rootPath) throws IOException {
		Path directory = Paths.get(rootPath);
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

		Files.walkFileTree(directory, opts, Integer.MAX_VALUE, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.deleteIfExists(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				throw exc;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (exc != null) {
					log.error("Failed to delete file of path {}", dir.toString());
					throw exc;
				}
				Files.deleteIfExists(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	public static void removeDirectoryRecursive(String rootPath) throws IOException {
		Path directory = Paths.get(rootPath);
		if (!Files.exists(directory)) {
			return;
		}
		deleteDirectory(directory);
	}

	private static void deleteDirectory(Path directory) throws IOException {
		if (!Files.isDirectory(directory)) {
			Files.delete(directory);
			return;
		}
		File[] files = directory.toFile().listFiles();
		for (File file : files) {
			deleteDirectory(file.toPath());
		}
		Files.delete(directory);
	}
}
