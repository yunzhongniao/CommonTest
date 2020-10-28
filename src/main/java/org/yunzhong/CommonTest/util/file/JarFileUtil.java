package org.yunzhong.CommonTest.util.file;

import org.springframework.core.io.ClassPathResource;

public class JarFileUtil {

	public void printFile(String path) {
		ClassPathResource res = new ClassPathResource(path);
	}
}
