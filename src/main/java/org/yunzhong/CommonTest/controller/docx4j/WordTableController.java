package org.yunzhong.CommonTest.controller.docx4j;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.docx4j.Docx4J;
import org.docx4j.dml.chart.CTBarChart;
import org.docx4j.dml.chart.CTBarSer;
import org.docx4j.dml.chart.CTPlotArea;
import org.docx4j.dml.chart.CTStrVal;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.CustomXmlPart;
import org.docx4j.openpackaging.parts.ExternalTarget;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.DrawingML.Chart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
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
@RequestMapping("docx4j/table")
@Api(value = "docx4j deal words")
public class WordTableController {

	private static final Logger log = LoggerFactory.getLogger(WordTableController.class);

	@ApiOperation(value = "replace table param")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public List<String> printStructure(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());

		List<String> result = new ArrayList<String>();

		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(wordPath.toFile());
		HashMap<String, CustomXmlPart> customXmlDataStorageParts = wordMLPackage.getPackage()
				.getCustomXmlDataStorageParts();
		for (Iterator<Entry<String, CustomXmlPart>> iterator = customXmlDataStorageParts.entrySet().iterator(); iterator
				.hasNext();) {
			Entry<String, CustomXmlPart> part = iterator.next();
			String key = part.getKey();
			CustomXmlPart value = part.getValue();
			log.info("custom xml part : {}", key);
		}
		HashMap<ExternalTarget, Part> externalResources = wordMLPackage.getExternalResources();
		for (Iterator<Entry<ExternalTarget, Part>> iterator = externalResources.entrySet().iterator(); iterator
				.hasNext();) {
			Entry<ExternalTarget, Part> entry = iterator.next();
			ExternalTarget key = entry.getKey();
			Part value = entry.getValue();
			log.info("external resource : {}", key.toString());
		}
		List<Relationship> relationships = wordMLPackage.getRelationshipsPart().getRelationships().getRelationship();
		for (Relationship relationship : relationships) {
			log.info("relationship id: {}, target: {}, targetMode:{}, type: {}", relationship.getId(),
					relationship.getTarget(), relationship.getTargetMode(), relationship.getType());
		}
		HashMap<PartName, Part> parts = wordMLPackage.getParts().getParts();
		for (Iterator<Entry<PartName, Part>> iterator = parts.entrySet().iterator(); iterator.hasNext();) {
			Entry<PartName, Part> next = iterator.next();
			PartName key = next.getKey();
			Part value = next.getValue();
			log.info("parts key: {}, uri: {}", key.getName(), key.getURI());
			if (key.getName().startsWith("/word/charts/chart")) {
				log.info("parts key: {}, uri: {}", key.getName(), key.getURI());
			}
		}
		return result;
	}

	@ApiOperation(value = "replace table param")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/replace/table", method = RequestMethod.GET)
	public void replaceTable(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		Path outPath = Paths.get(parentPathURL.getPath(), "replace-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());
		log.info("out file path {}", outPath.toString());

		UserTableModel userTableModel = JsonUtil.fromJson(Files.readString(jsonPath), UserTableModel.class);
		Map<String, String> params = parseToMap(userTableModel);
		Map<String, String> keyParams = parseToKeyMap(userTableModel);

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
		MainDocumentPart documentPart = template.getMainDocumentPart();
		VariablePrepare.prepare(template);
		documentPart.variableReplace(params);

		Part part = template.getParts().get(new PartName("/word/charts/chart1.xml"));
		Chart chart = (Chart) part;
		String xml = chart.getXML();
		log.info("chart1 xml :{}", xml);
		CTPlotArea plotArea = chart.getJaxbElement().getChart().getPlotArea();
		List<Object> objects = plotArea.getAreaChartOrArea3DChartOrLineChart();
		// update chart values in doc
		for (Object object : objects) {
			if (object instanceof CTBarChart) {
				List<CTBarSer> ctBarSers = ((CTBarChart) object).getSer();
				for (CTBarSer ctBarSer : ctBarSers) {
					List<CTStrVal> pts = ctBarSer.getTx().getStrRef().getStrCache().getPt();
					if (!CollectionUtils.isEmpty(pts)) {
						for (CTStrVal pt : pts) {
							String value = pt.getV();
							if (keyParams.containsKey(value)) {
								pt.setV(keyParams.get(value));
							}
						}
					}
				}
			}
		}
		String xmlAfterUpdate = chart.getXML();
		log.info("chart1 after update xml :{}", xmlAfterUpdate);
		Docx4J.save(template, outPath.toFile());
	}

	private Map<String, String> parseToMap(UserTableModel userTableModel) {
		if (userTableModel == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isEmpty(userTableModel.getClassName())) {
			result.put("userStat.className", userTableModel.getClassName());
		} else {
			result.put("userStat.className", "");
		}
		if (!CollectionUtils.isEmpty(userTableModel.getUserStats())) {
			for (int i = 0; i < userTableModel.getUserStats().size(); i++) {
				UserStat userStat = userTableModel.getUserStats().get(i);
				String titleName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "title");
				String titleValue = userStat.getTitle();
				result.put(titleName, titleValue);

				String userCountName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "userCount");
				String userCountValue = userStat.getUserCount().toString();
				result.put(userCountName, userCountValue);

				String perName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "per");
				String perValue = userStat.getPer().toString();
				result.put(perName, perValue);
			}
		}

		return result;
	}

	private Map<String, String> parseToKeyMap(UserTableModel userTableModel) {
		if (userTableModel == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isEmpty(userTableModel.getClassName())) {
			result.put("${userStat.className}", userTableModel.getClassName());
		} else {
			result.put("${userStat.className}", "");
		}
		if (!CollectionUtils.isEmpty(userTableModel.getUserStats())) {
			for (int i = 0; i < userTableModel.getUserStats().size(); i++) {
				UserStat userStat = userTableModel.getUserStats().get(i);
				String titleName = String.format("${%s.%s.%s}", "userStat.userStats", String.valueOf(i), "title");
				String titleValue = userStat.getTitle();
				result.put(titleName, titleValue);

				String userCountName = String.format("${%s.%s.%s}", "userStat.userStats", String.valueOf(i),
						"userCount");
				String userCountValue = userStat.getUserCount().toString();
				result.put(userCountName, userCountValue);

				String perName = String.format("${%s.%s.%s}", "userStat.userStats", String.valueOf(i), "per");
				String perValue = userStat.getPer().toString();
				result.put(perName, perValue);
			}
		}

		return result;
	}
}
