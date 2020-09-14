package org.yunzhong.CommonTest.controller.poi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("html2word")
@Api(value = "html to word")
public class HTML2WordController {

	private static final Logger log = LoggerFactory.getLogger(HTML2WordController.class);

	@ApiOperation(value = "convert html to word")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "fileName", required = true, defaultValue = "words.html") })
	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	public void convert(@RequestParam String fileName) throws URISyntaxException, IOException {
		URL parentPathURL = this.getClass().getClassLoader().getResource("templates");
		Path resourcePath = Paths.get(parentPathURL.getPath(), fileName);
		Path outPath = Paths.get(parentPathURL.getPath(), "words.doc");
		log.info("resource {}", resourcePath.toString());
		log.info("out path {}", outPath.toString());
		POIFSFileSystem poiFileSystem = new POIFSFileSystem();
		try {
			InputStream stream = Files.newInputStream(resourcePath);
			OutputStream outputStream = Files.newOutputStream(outPath);
			poiFileSystem.createDocument(stream, "Document");
			poiFileSystem.writeFilesystem(outputStream);
		} finally {
			poiFileSystem.close();
		}
	}

	@ApiOperation(value = "convert html to word with table")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "fileName", required = true, defaultValue = "tables.html") })
	@RequestMapping(value = "/convert/with/table", method = RequestMethod.GET)
	public void convertWithTable(@RequestParam String fileName) throws URISyntaxException, IOException {
		URL parentPathURL = this.getClass().getClassLoader().getResource("templates");
		Path resourcePath = Paths.get(parentPathURL.getPath(), fileName);
		Path outPath = Paths.get(parentPathURL.getPath(), "tables.doc");
		log.info("resource {}", resourcePath.toString());
		log.info("out path {}", outPath.toString());
		POIFSFileSystem poiFileSystem = new POIFSFileSystem();
		try {
			InputStream stream = Files.newInputStream(resourcePath);
			OutputStream outputStream = Files.newOutputStream(outPath);
			poiFileSystem.createDocument(stream, "Document");
			poiFileSystem.writeFilesystem(outputStream);
		} finally {
			poiFileSystem.close();
		}
	}

	@ApiOperation(value = "convert html to word with image")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "fileName", required = true, defaultValue = "words.html") })
	@RequestMapping(value = "/convert/with/image", method = RequestMethod.GET)
	public void convertWithImage(@RequestParam String fileName) throws URISyntaxException, IOException {
		URL parentPathURL = this.getClass().getClassLoader().getResource("templates");
		Path resourcePath = Paths.get(parentPathURL.getPath(), fileName);
		Path outPath = Paths.get(parentPathURL.getPath(), "images.doc");
		log.info("resource {}", resourcePath.toString());
		log.info("out path {}", outPath.toString());
		POIFSFileSystem poiFileSystem = new POIFSFileSystem();
		try {
			InputStream stream = Files.newInputStream(resourcePath);
			OutputStream outputStream = Files.newOutputStream(outPath);
			poiFileSystem.createDocument(stream, "Document");
			poiFileSystem.writeFilesystem(outputStream);
		} finally {
			poiFileSystem.close();
		}
	}
}
