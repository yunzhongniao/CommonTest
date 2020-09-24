package org.yunzhong.CommonTest.controller.docx4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.docx4j.Docx4J;
import org.docx4j.dml.chart.CTBarChart;
import org.docx4j.dml.chart.CTBarSer;
import org.docx4j.dml.chart.CTNumVal;
import org.docx4j.dml.chart.CTStrVal;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.DrawingML.Chart;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.docx4j.openpackaging.parts.WordprocessingML.EmbeddedPackagePart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.utils.BufferUtil;
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
import org.yunzhong.CommonTest.model.UserTableModel;
import org.yunzhong.CommonTest.model.UserTableModel.UserStat;
import org.yunzhong.CommonTest.util.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("docx4j/chart/v2")
@Api(value = "docx4j deal words v2")
public class WordChartV2Controller {

	private static final Logger log = LoggerFactory.getLogger(WordChartV2Controller.class);

	@ApiOperation(value = "replace table param and format type")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/replace/file", method = RequestMethod.GET)
	public void replaceTableTypeFormat(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path dataXlsxPath = Paths.get(parentPathURL.getPath(), "replaceTable2", "Workbook1.xlsx");
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		Path outPath = Paths.get(parentPathURL.getPath(), "replace-v2-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());
		log.info("out file path {}", outPath.toString());

		UserTableModel userTableModel = JsonUtil.fromJson(Files.readString(jsonPath), UserTableModel.class);
		Map<String, String> params = parseToMap(userTableModel);
		Map<String, String> keyParams = parseToKeyMap(userTableModel);
		Map objParams = parseToObjMap(userTableModel);

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
		MainDocumentPart mainDocumentPart = template.getMainDocumentPart();

		EmbeddedPackagePart embeddedPackagePart = (EmbeddedPackagePart) template.getParts()
				.get(new PartName("/word/embeddings/Workbook1.xlsx"));
		RelationshipsPart relationshipsPartOld = embeddedPackagePart.getRelationshipsPart();
		embeddedPackagePart.setBinaryData(Files.readAllBytes(dataXlsxPath));
		RelationshipsPart relationshipsPartNew = embeddedPackagePart.getRelationshipsPart();

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

	@ApiOperation(value = "replace table param and format type")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx"),
			@ApiImplicitParam(dataType = "String", name = "jsonName", required = true, defaultValue = "replaceTable.json") })
	@RequestMapping(value = "/replace/cell", method = RequestMethod.GET)
	public void replaceTableCell(@RequestParam String wordName, @RequestParam String jsonName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		Path jsonPath = Paths.get(parentPathURL.getPath(), jsonName);
		Path outPath = Paths.get(parentPathURL.getPath(), "replace-v2-cell-" + wordName);
		log.info("source word file {}", wordPath.toString());
		log.info("json param path {}", jsonPath.toString());
		log.info("out file path {}", outPath.toString());

		UserTableModel userTableModel = JsonUtil.fromJson(Files.readString(jsonPath), UserTableModel.class);

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
		MainDocumentPart mainDocumentPart = template.getMainDocumentPart();

		Map<String, String> params = parseToMap(userTableModel);
		Map<String, String> keyParams = parseToKeyMap(userTableModel);
//		VariablePrepare.prepare(template);
		mainDocumentPart.variableReplace(params);

		Chart chart = (Chart) template.getParts().get(new PartName("/word/charts/chart1.xml"));
		List<Object> objects = chart.getJaxbElement().getChart().getPlotArea().getAreaChartOrArea3DChartOrLineChart();

		for (Object object : objects) {
			if (object instanceof CTBarChart) {
				List<CTBarSer> ctBarSers = ((CTBarChart) object).getSer();
				for (int j = 0; j < ctBarSers.size(); j++) {// 列
					CTBarSer ctBarSer = ctBarSers.get(j);
					List<CTNumVal> ctNumVals = ctBarSer.getVal().getNumRef().getNumCache().getPt();
					for (int i = 0; i < ctNumVals.size(); i++) {
						CTNumVal ctNumVal = ctNumVals.get(i);
						System.out.println("values :" + ctNumVal.getV());
						UserStat userStat = userTableModel.getUserStats().get(j);
						if (i == 0) {
							ctNumVal.setV(userStat.getUserCount().toString());
						} else {
							ctNumVal.setV(userStat.getPer().toString());
						}
						System.out.println("new values :" + ctNumVal.getV());
					}
				}
			}
		}

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

		EmbeddedPackagePart epp = (EmbeddedPackagePart) template.getParts()
				.get(new PartName("/word/embeddings/Workbook1.xlsx"));
		if (epp == null) {
			throw new Exception("Could find EmbeddedPackagePart: /word/embeddings/Workbook1.xlsx ");
		}
		InputStream is = BufferUtil.newInputStream(epp.getBuffer());

		SpreadsheetMLPackage spreadSheet = (SpreadsheetMLPackage) SpreadsheetMLPackage.load(is);
		Map<PartName, Part> partsMap = spreadSheet.getParts().getParts();
		Iterator<Entry<PartName, Part>> it = partsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<PartName, Part> pairs = it.next();
			if (partsMap.get(pairs.getKey()) instanceof WorksheetPart) {
				WorksheetPart wsp = (WorksheetPart) partsMap.get(pairs.getKey());
				List<Row> rows = wsp.getJaxbElement().getSheetData().getRow();
				for (int rowNum = 1; rowNum < rows.size(); rowNum++) {
					Row row = rows.get(rowNum);
					List<Cell> cells = row.getC();
					for (int colNum = 1; colNum < cells.size(); colNum++) {
						Cell cell = cells.get(colNum);
						UserStat userStat = userTableModel.getUserStats().get(colNum - 1);
						if (cell.getR().endsWith("2")) {
							System.out.println(cell.getR() + " CELL VAL: " + cell.getV());
							cell.setT(STCellType.N);
							cell.setV(userStat.getUserCount().toString());
							System.out.println(cell.getR() + " CELL new VAL: " + cell.getV());
						} else {
							System.out.println(cell.getR() + " CELL VAL: " + cell.getV());
							cell.setT(STCellType.N);
							cell.setV(userStat.getPer().toString());
							System.out.println(cell.getR() + " CELL new VAL: " + cell.getV());
						}
					}
				}
			}
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Save saver = new Save(spreadSheet);
		saver.save(baos);
		epp.setBinaryData(baos.toByteArray());
		Docx4J.save(template, outPath.toFile());
	}

	@ApiOperation(value = "print word relations")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx") })
	@RequestMapping(value = "/relations/print", method = RequestMethod.GET)
	public void printRelation(@RequestParam String wordName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		log.info("source word file {}", wordPath.toString());

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
		List<Relationship> relationships = template.getRelationshipsPart().getRelationships().getRelationship();
		for (Relationship relationship : relationships) {
			String type = relationship.getType();
			String id = relationship.getId();
			log.info("get relation type {}, id {}, target {}", type, id, relationship.getTarget());
		}
		List<Relationship> sourceRelationships = template.getMainDocumentPart().getSourceRelationships();
		for (Relationship relationship : sourceRelationships) {
			String type = relationship.getType();
			String id = relationship.getId();
			log.info("get main document source relation type {}, id {}, target {}", type, id, relationship.getTarget());
		}
		List<Relationship> maindocRelationships = template.getMainDocumentPart().getRelationshipsPart()
				.getRelationships().getRelationship();
		for (Relationship relationship : maindocRelationships) {
			log.info("get main relation ship type {}, id {}, target {},target mode {}", relationship.getType(),
					relationship.getId(), relationship.getTarget(), relationship.getTargetMode());
		}
	}

	@ApiOperation(value = "print chart relations")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "String", name = "wordName", required = true, defaultValue = "replaceTable2.docx") })
	@RequestMapping(value = "/relations/chart/print", method = RequestMethod.GET)
	public void printChartRelation(@RequestParam String wordName) throws Exception {
		URL parentPathURL = this.getClass().getClassLoader().getResource("doc-templates");
		Path wordPath = Paths.get(parentPathURL.getPath(), wordName);
		log.info("source word file {}", wordPath.toString());

		WordprocessingMLPackage template = WordprocessingMLPackage.load(wordPath.toFile());
		List<Relationship> maindocRelationships = template.getMainDocumentPart().getRelationshipsPart()
				.getRelationships().getRelationship();
		for (Relationship relationship : maindocRelationships) {
			String target = relationship.getTarget();
			if (relationship.getType().endsWith("/relationships/chart")) { // 图表
				log.info("get main relation chart type {}, id {}, target {},target mode {}", relationship.getType(),
						relationship.getId(), target, relationship.getTargetMode());
				Chart chart = (Chart) template.getParts().get(new PartName("/word/" + target));
				List<Relationship> chartRelations = chart.getRelationshipsPart().getRelationships().getRelationship();
				String xlsxName = null;
				for (Relationship chartRelation : chartRelations) {
					if (chartRelation.getType().endsWith("/relationships/package") // 图表关联的xlsx
							&& chartRelation.getTarget().endsWith(".xlsx")) {
						log.info("chart xlsx {} relation {} target {} type {}", target, chartRelation.getId(),
								chartRelation.getTarget(), chartRelation.getType());
						xlsxName = chartRelation.getTarget().replaceFirst("../", "/word/");// "/word/embeddings/Workbook1.xlsx"
						log.info("chart xlsx {} xlsxName {}", target, xlsxName);
					}
				}
			}
		}
	}
}
