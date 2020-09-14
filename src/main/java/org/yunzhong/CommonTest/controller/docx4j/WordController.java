package org.yunzhong.CommonTest.controller.docx4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yunzhong.CommonTest.model.UserTableModel;
import org.yunzhong.CommonTest.model.UserTableModel.UserStat;
import org.yunzhong.CommonTest.util.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("docx4j")
@Api(value = "docx4j deal words")
public class WordController {

	private static final Logger log = LoggerFactory.getLogger(WordController.class);

	@ApiOperation(value = "replace word param")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceWord.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceWord.json") })
	@RequestMapping(value = "/replace", method = RequestMethod.GET)
	public void replace(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		Path outPath = Paths.get(parentPathURL.getPath(), "replace-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());
		log.info("out file path {}", outPath.toString());

		Map<String, String> params = JsonUtil.fromJson(Files.readString(jsonPath), Map.class);
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(wordPath.toFile());
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		VariablePrepare.prepare(wordMLPackage);
		documentPart.variableReplace(params);
		wordMLPackage.save(outPath.toFile());
	}

	@ApiOperation(value = "print word content")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceWord.docx") })
	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public void print(@RequestParam String wordName) {
		try {
			URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
			Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(wordPath.toFile());
			String contentType = wordMLPackage.getContentType();
			log.info("contentType:" + contentType);
			MainDocumentPart part = wordMLPackage.getMainDocumentPart();
			List<Object> list = part.getContent();
			System.out.println("content -> body -> " + part.getContents().getBody().toString());
			for (Object o : list) {
				log.info("info:" + o);
			}
		} catch (Exception e) {
			log.error("Failed to read file ", e);
		}
	}

	@ApiOperation(value = "append word param")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "appendWord.docx"),
			@ApiImplicitParam(dataType = "String", name = "appendContent", required = true, defaultValue = "lalala test words") })
	@RequestMapping(value = "/append", method = RequestMethod.GET)
	public void append(@RequestParam String wordName, @RequestParam String appendContent)
			throws URISyntaxException, IOException {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path outPath = Paths.get(parentPathURL.getPath(), "append-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("out file path {}", outPath.toString());
		if (Files.exists(outPath)) {
			Files.delete(outPath);
		}
		WordprocessingMLPackage wordprocessingMLPackage;
		try {
			// 先加载word文档
			wordprocessingMLPackage = WordprocessingMLPackage.load(wordPath.toFile());
			// 增加内容
			wordprocessingMLPackage.getMainDocumentPart().addParagraphOfText("你好!");
			wordprocessingMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "这是标题!");
			wordprocessingMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle", " 这是二级标题!");
			// 保存文档
			wordprocessingMLPackage.save(outPath.toFile());
		} catch (Docx4JException e) {
			log.error("addParagraph to docx error: Docx4JException", e);
		}
	}
}
