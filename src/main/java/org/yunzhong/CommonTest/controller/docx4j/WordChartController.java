package org.yunzhong.CommonTest.controller.docx4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.Docx4J;
import org.docx4j.dml.chart.CTBarChart;
import org.docx4j.dml.chart.CTBarSer;
import org.docx4j.dml.chart.CTPlotArea;
import org.docx4j.dml.chart.CTStrVal;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.DrawingML.Chart;
import org.docx4j.openpackaging.parts.SpreadsheetML.JaxbSmlPart;
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.docx4j.openpackaging.parts.WordprocessingML.EmbeddedPackagePart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.xlsx4j.sml.SheetData;
import org.yunzhong.CommonTest.model.UserTableModel;
import org.yunzhong.CommonTest.model.UserTableModel.UserStat;
import org.yunzhong.CommonTest.util.JsonUtil;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("docx4j/chart")
@Api(value = "docx4j deal words")
public class WordChartController {

	private static final Logger log = LoggerFactory.getLogger(WordChartController.class);

	@ApiOperation(value = "replace table param")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/replace", method = RequestMethod.GET)
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
		EmbeddedPackagePart xlsxDataPart = (EmbeddedPackagePart) template.getParts()
				.get(new PartName("/word/embeddings/Workbook1.xlsx"));

		ByteArrayInputStream xlsxInputStream = new ByteArrayInputStream(xlsxDataPart.getBytes());
		SpreadsheetMLPackage xlsxPackge = SpreadsheetMLPackage.load(xlsxInputStream);
		JaxbSmlPart smlPart = (JaxbSmlPart) xlsxPackge.getParts().get(new PartName("/xl/sharedStrings.xml"));
		smlPart.variableReplace(params);

		ByteArrayOutputStream xlsxOutputStream = new ByteArrayOutputStream();
		xlsxPackge.save(xlsxOutputStream);

		xlsxDataPart.setBinaryData(xlsxOutputStream.toByteArray());
		template.getParts().put(xlsxDataPart);

		Docx4J.save(template, outPath.toFile());
	}

	@ApiOperation(value = "replace table param self param data type")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/replace/slef-def", method = RequestMethod.GET)
	public void replaceTableSelf(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		Path outPath = Paths.get(parentPathURL.getPath(), "replace-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());
		log.info("out file path {}", outPath.toString());

		UserTableModel userTableModel = JsonUtil.fromJson(Files.readString(jsonPath), UserTableModel.class);
		Map<String, String> params = parseToMap(userTableModel);
		Map objParams = parseToObjMap(userTableModel);

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
		MainDocumentPart documentPart = template.getMainDocumentPart();
		VariablePrepare.prepare(template);
		documentPart.variableReplace(params);

		EmbeddedPackagePart xlsxDataPart = (EmbeddedPackagePart) template.getParts()
				.get(new PartName("/word/embeddings/Workbook1.xlsx"));

		ByteArrayInputStream xlsxInputStream = new ByteArrayInputStream(xlsxDataPart.getBytes());
		SpreadsheetMLPackage xlsxPackge = SpreadsheetMLPackage.load(xlsxInputStream);
		SharedStrings smlPart = (SharedStrings) xlsxPackge.getParts().get(new PartName("/xl/sharedStrings.xml"));
		smlPart.variableReplace(objParams);

		ByteArrayOutputStream xlsxOutputStream = new ByteArrayOutputStream();
		xlsxPackge.save(xlsxOutputStream);

		xlsxDataPart.setBinaryData(xlsxOutputStream.toByteArray());
		template.getParts().put(xlsxDataPart);

		Docx4J.save(template, outPath.toFile());
	}

	@ApiOperation(value = "replace table param and format type")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/replace/typeFormat", method = RequestMethod.GET)
	public void replaceTableTypeFormat(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		Path outPath = Paths.get(parentPathURL.getPath(), "replace-format-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());
		log.info("out file path {}", outPath.toString());

		UserTableModel userTableModel = JsonUtil.fromJson(Files.readString(jsonPath), UserTableModel.class);
		Map<String, String> params = parseToMap(userTableModel);
		Map<String, String> keyParams = parseToKeyMap(userTableModel);
		Map objParams = parseToObjMap(userTableModel);

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());

		Part part = template.getParts().get(new PartName("/word/charts/chart1.xml"));
		Chart chart = (Chart) part;
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

		MainDocumentPart documentPart = template.getMainDocumentPart();
		VariablePrepare.prepare(template);
		documentPart.variableReplace(params);

		EmbeddedPackagePart xlsxDataPart = (EmbeddedPackagePart) template.getParts()
				.get(new PartName("/word/embeddings/Workbook1.xlsx"));
		ByteArrayInputStream xlsxInputStream = new ByteArrayInputStream(xlsxDataPart.getBytes());
		SpreadsheetMLPackage xlsxPackge = SpreadsheetMLPackage.load(xlsxInputStream);
		SharedStrings smlPart = (SharedStrings) xlsxPackge.getParts().get(new PartName("/xl/sharedStrings.xml"));
		smlPart.variableReplace(objParams);

		ByteArrayOutputStream xlsxOutputStream = new ByteArrayOutputStream();
		xlsxPackge.save(xlsxOutputStream);
		xlsxDataPart.setBinaryData(xlsxOutputStream.toByteArray());
		template.getParts().put(xlsxDataPart);

		
		EmbeddedPackagePart xlsxDataPart2 = (EmbeddedPackagePart) template.getParts()
				.get(new PartName("/word/embeddings/Workbook1.xlsx"));
		ByteArrayInputStream xlsxInputStream2 = new ByteArrayInputStream(xlsxDataPart2.getBytes());
		SpreadsheetMLPackage xlsxPackge2 = SpreadsheetMLPackage.load(xlsxInputStream2);
		// change data format
		WorksheetPart worksheet = xlsxPackge2.getWorkbookPart().getWorksheet(0);
		SheetData sheetData = worksheet.getContents().getSheetData();
		List<Row> rows = sheetData.getRow();
		List<String> cellPosi = Lists.asList("B2", new String[] { "B3", "C2", "C3", "D2", "D3" });
		for (Row row : rows) {
			for (Cell cell : row.getC()) {
				if (cellPosi.contains(cell.getR())) {
					log.info("cell :{}, type: {}", cell.getR(), cell.getT());
					cell.setT(STCellType.N);
				}
			}
		}
		ByteArrayOutputStream xlsxOutputStream2 = new ByteArrayOutputStream();
		xlsxPackge2.save(xlsxOutputStream2);
		xlsxDataPart2.setBinaryData(xlsxOutputStream2.toByteArray());
		template.getParts().put(xlsxDataPart2);
		
		Docx4J.save(template, outPath.toFile());
	}

	private Map<String, String> parseToMap(UserTableModel userTableModel) {
		if (userTableModel == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		if (!StringUtils.isEmpty(userTableModel.getClassName())) {
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

	private Map<String, Object> parseToObjMap(UserTableModel userTableModel) {
		if (userTableModel == null) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(userTableModel.getClassName())) {
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
				result.put(userCountName, Long.valueOf(userStat.getUserCount()));

				String perName = String.format("%s.%s.%s", "userStat.userStats", String.valueOf(i), "per");
				result.put(perName, Double.valueOf(userStat.getPer()));
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
