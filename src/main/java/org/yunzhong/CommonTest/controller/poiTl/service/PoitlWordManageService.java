package org.yunzhong.CommonTest.controller.poiTl.service;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ClearHandler;
import com.deepoove.poi.config.Configure.ELMode;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.data.TextRenderData;

/**
 * @author yunzhong
 *
 */
@Service
public class PoitlWordManageService {

    /**
     * @param wordPath wordPath
     * @param outPath  outPath
     * @throws Exception
     */
    public void replaceData(Path wordPath, Path outPath) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        ConfigureBuilder builder = Configure.newBuilder().setElMode(ELMode.POI_TL_STANDARD_MODE)
                .setValidErrorHandler(new ClearHandler());
        XWPFTemplate template = XWPFTemplate.compile(wordPath.toFile(), builder.build()).render(paramMap);

        FileOutputStream out = new FileOutputStream(outPath.toFile());
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    /**
     * @param wordPath
     * @param outPath
     * @throws Exception
     */
    public void replaceChart(Path wordPath, Path outPath) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("className", "poiTemplateName");
        ConfigureBuilder builder = Configure.newBuilder().setElMode(ELMode.POI_TL_STANDARD_MODE)
                .setValidErrorHandler(new ClearHandler());

        ChartMultiSeriesRenderData chart = new ChartMultiSeriesRenderData();
        chart.setChartTitle("语言统计");
        chart.setCategories(new String[] { "中文", "English" });
        List<SeriesRenderData> seriesRenderData = new ArrayList<>();
        seriesRenderData.add(new SeriesRenderData("countries", new Double[] { 15.0, 6.0 }));
        seriesRenderData.add(new SeriesRenderData("speakers", new Double[] { 223.0, 119.0 }));
        chart.setSeriesDatas(seriesRenderData);
        paramMap.put("languageChart", chart);

        ChartSingleSeriesRenderData pie = new ChartSingleSeriesRenderData();
        pie.setChartTitle("国家派");
        pie.setCategories(new String[] { "俄罗斯", "加拿大", "美国", "中国" });
        pie.setSeriesData(new SeriesRenderData("countries", new Integer[] { 17098242, 9984670, 9826675, 9596961 }));
        paramMap.put("pieChart", pie);

        XWPFTemplate template = XWPFTemplate.compile(wordPath.toFile(), builder.build()).render(paramMap);

        FileOutputStream out = new FileOutputStream(outPath.toFile());
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    public void replaceTable(Path wordPath, Path outPath) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("className", "poiTemplateName");
        ConfigureBuilder builder = Configure.newBuilder().setElMode(ELMode.POI_TL_STANDARD_MODE)
                .setValidErrorHandler(new ClearHandler());

        RowRenderData header = RowRenderData.build(new TextRenderData("姓名"),
                new TextRenderData("学历"));
        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");

        paramMap.put("table1", new MiniTableRenderData(header, Arrays.asList(row0, row1)));

        XWPFTemplate template = XWPFTemplate.compile(wordPath.toFile(), builder.build()).render(paramMap);

        FileOutputStream out = new FileOutputStream(outPath.toFile());
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
