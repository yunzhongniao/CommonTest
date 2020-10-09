package org.yunzhong.CommonTest.controller.docx4j;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yunzhong.CommonTest.controller.docx4j.module.ParamData;
import org.yunzhong.CommonTest.controller.docx4j.service.WordManageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("docx4j/word/manage")
@Api(value = "docx4j mange words")
public class WordManageController {
    private static final Logger log = LoggerFactory.getLogger(WordManageController.class);

    @Autowired
    private WordManageService wordManageService;

    @ApiOperation(value = "replace data")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "manageword.docx"),
            @ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
    @RequestMapping(value = "/replace/chart/data", method = RequestMethod.GET)
    public void replaceData(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
        URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
        Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
        Path paramPath = Paths.get(parentPathURL.getPath(), jsonName);
        Path outPath = Paths.get(parentPathURL.getPath(), "manage-chart-data-" + wordName);
        log.info("source word file {}", wordPath.toString());
        log.info("json param path {}", paramPath.toString());
        log.info("out file path {}", outPath.toString());
        ParamData paramData = ParamData.load(paramPath);
        WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
        wordManageService.replaceMainDoc(template, paramData);
        wordManageService.replaceChart(template, paramData);
        Docx4J.save(template, outPath.toFile());
    }

    @ApiOperation(value = "print docx structure")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "manageword.docx") })
    @RequestMapping(value = "/print/chart", method = RequestMethod.GET)
    public void printChartRelation(@RequestParam String wordName) throws Exception {
        URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
        Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
        log.info("source word file {}", wordPath.toString());
        WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
        wordManageService.printChartRelation(template);
    }
}
