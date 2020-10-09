package org.yunzhong.CommonTest.controller.poiTl;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yunzhong.CommonTest.controller.poiTl.service.PoitlWordManageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("poitl/word/manage")
@Api(value = "poitl mange words")
public class TemplateWordManageController {
    @Autowired
    private PoitlWordManageService wordManageService;

    @ApiOperation(value = "replace data")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "poitlmanageword.docx"),
            @ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
    @RequestMapping(value = "/replace/data", method = RequestMethod.GET)
    public void replaceData(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
        URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
        Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
        Path paramPath = Paths.get(parentPathURL.getPath(), jsonName);
        Path outPath = Paths.get(parentPathURL.getPath(), "poitl-manage-data-" + wordName);
        wordManageService.replaceData(wordPath, paramPath, outPath);
    }
}
