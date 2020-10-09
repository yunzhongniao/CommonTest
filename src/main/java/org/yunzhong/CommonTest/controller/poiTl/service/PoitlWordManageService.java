package org.yunzhong.CommonTest.controller.poiTl.service;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.HashMap;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ClearHandler;
import com.deepoove.poi.config.Configure.ELMode;
import com.deepoove.poi.config.ConfigureBuilder;

/**
 * @author yunzhong
 *
 */
public class PoitlWordManageService {

    public void replaceData(Path wordPath, Path paramPath, Path outPath) throws Exception {
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

}
