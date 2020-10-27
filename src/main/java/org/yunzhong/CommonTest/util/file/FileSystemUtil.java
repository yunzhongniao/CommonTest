package org.yunzhong.CommonTest.util.file;

import java.io.IOException;
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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author yunzhong
 *
 */
public class FileSystemUtil {
	private static final Logger log = LoggerFactory.getLogger(FileSystemUtil.class);

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
}
