package org.yunzhong.CommonTest.controller.docx4j.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.docx4j.dml.CTRegularTextRun;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.dml.chart.CTBarChart;
import org.docx4j.dml.chart.CTBarSer;
import org.docx4j.dml.chart.CTNumVal;
import org.docx4j.dml.chart.CTStrVal;
import org.docx4j.dml.chart.CTTitle;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.DrawingML.Chart;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.docx4j.openpackaging.parts.WordprocessingML.EmbeddedPackagePart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.utils.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.yunzhong.CommonTest.controller.docx4j.module.ParamData;
import org.yunzhong.CommonTest.controller.docx4j.module.ParamData.ParamRow;

@Service
public class WordManageService {
    public static final Logger log = LoggerFactory.getLogger(WordManageService.class);

    /**
     * @param template
     * @param paramData
     * @throws Exception
     */
    public void replaceMainDoc(WordprocessingMLPackage template, ParamData paramData) throws Exception {
        MainDocumentPart documentPart = template.getMainDocumentPart();
        VariablePrepare.prepare(template);
        documentPart.variableReplace(paramData.getVariables());
    }

    /**
     * @param template
     * @param paramMap
     * @throws Exception
     */
    public void replaceChart(WordprocessingMLPackage template, ParamData paramMap) throws Exception {
        List<Relationship> maindocRelationships = template.getMainDocumentPart().getRelationshipsPart()
                .getRelationships().getRelationship();
        Integer index = 0;
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
                    }
                }
                assembleChart(chart, paramMap, index);
                if (xlsxName != null) {
                    EmbeddedPackagePart epp = (EmbeddedPackagePart) template.getParts().get(new PartName(xlsxName));
                    InputStream is = BufferUtil.newInputStream(epp.getBuffer());
                    SpreadsheetMLPackage spreadSheet = (SpreadsheetMLPackage) SpreadsheetMLPackage.load(is);
                    assembleXlsx(spreadSheet, paramMap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Save saver = new Save(spreadSheet);
                    saver.save(baos);
                    epp.setBinaryData(baos.toByteArray());
                }
            }
            index++;
        }
    }

    private void assembleXlsx(SpreadsheetMLPackage spreadSheet, ParamData paramMap) {
        Map<PartName, Part> partsMap = spreadSheet.getParts().getParts();
        String title = spreadSheet.getTitle();
        System.out.println("spread sheet title :" + title);
        Iterator<Entry<PartName, Part>> it = partsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<PartName, Part> pairs = it.next();
            if (partsMap.get(pairs.getKey()) instanceof WorksheetPart) {
                WorksheetPart wsp = (WorksheetPart) partsMap.get(pairs.getKey());
                List<Row> rows = wsp.getJaxbElement().getSheetData().getRow();
                List<ParamRow> paramRows = paramMap.getDatas().get(title);
                for (int rowNum = 1; rowNum < rows.size(); rowNum++) {
                    Row row = rows.get(rowNum);
                    ParamRow paramRow = paramRows.get(rowNum);
                    List<Cell> cells = row.getC();
                    for (int colNum = 1; colNum < cells.size(); colNum++) {
                        Cell cell = cells.get(colNum);
                        String value = paramRow.getValues().get(colNum);
                        System.out.println(cell.getR() + " CELL VAL: " + cell.getV());
                        cell.setT(STCellType.N);
                        cell.setV(value);
                        System.out.println(cell.getR() + " CELL new VAL: " + cell.getV());
                    }
                }
            }
        }

    }

    private void assembleChart(Chart chart, ParamData paramMap, Integer index) {
        CTTitle title = chart.getJaxbElement().getChart().getTitle();
        List<Object> objects = chart.getJaxbElement().getChart().getPlotArea().getAreaChartOrArea3DChartOrLineChart();

        for (Object object : objects) {
            if (object instanceof CTBarChart) {
                List<CTBarSer> ctBarSers = ((CTBarChart) object).getSer();
                List<ParamRow> paramRows = paramMap.getDatas().get(index.toString());
                for (int j = 0; j < ctBarSers.size(); j++) {// 列
                    CTBarSer ctBarSer = ctBarSers.get(j);
                    List<CTNumVal> ctNumVals = ctBarSer.getVal().getNumRef().getNumCache().getPt();
                    ParamRow paramRow = paramRows.get(j);
                    for (int i = 0; i < ctNumVals.size(); i++) {
                        CTNumVal ctNumVal = ctNumVals.get(i);
                        System.out.println("values :" + ctNumVal.getV());
                        String value = paramRow.getValues().get(i);
                        ctNumVal.setV(value);
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
                            if (paramMap.getVariables().containsKey(value)) {
                                pt.setV(paramMap.getVariables().get(value));
                            }
                        }
                    }
                }
            }
        }
    }

    public void printChartRelation(WordprocessingMLPackage template) throws Exception {
        List<Relationship> maindocRelationships = template.getMainDocumentPart().getRelationshipsPart()
                .getRelationships().getRelationship();
        for (Relationship relationship : maindocRelationships) {
            String target = relationship.getTarget();
            if (relationship.getType().endsWith("/relationships/chart")) { // 图表
                log.info("get main relation chart type {}, id {}, target {},target mode {}", relationship.getType(),
                        relationship.getId(), target, relationship.getTargetMode());
                Chart chart = (Chart) template.getParts().get(new PartName("/word/" + target));
                String xml = chart.getXML();
                log.info("get main relation chart type {}, id {}, target {},xml {}", relationship.getType(),
                        relationship.getId(), target, xml);
                CTTitle title = chart.getContents().getChart().getTitle();
                if (title.getTx() != null && title.getTx().getRich() != null) {
                    List<CTTextParagraph> pList = title.getTx().getRich().getP();
                    if (!CollectionUtils.isEmpty(pList)) {
                        CTTextParagraph ctTextParagraph = pList.get(0);
                        List<Object> egTextRun = ctTextParagraph.getEGTextRun();
                        if (!CollectionUtils.isEmpty(egTextRun)) {
                            CTRegularTextRun object = (CTRegularTextRun) egTextRun.get(0);
                            String t = object.getT();
                            log.info("get main relation chart type {}, id {}, target {}, title {}",
                                    relationship.getType(), relationship.getId(), target, t);
                        }
                    }
                }
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
