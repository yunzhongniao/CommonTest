package org.yunzhong.CommonTest.echarts.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yunzhong
 *
 */
public class MarketSpreadService {

    public static class HeatDataPoint {
        int x;
        int y;
        double quot;
        int bsFlag;

        public HeatDataPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class ScatterDataPoint {
        int x;
        int y;
        double matchQty;
        int bsFlag;
    }

    public Object getData() {
        double riseLimit = 178D;
        double fallLimit = 150D;
        double stick = 1;
        int xCount = 1000;
        int yCount = (int) ((riseLimit - fallLimit) / stick);
        List<HeatDataPoint> heatData = new ArrayList<>();
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                heatData.add(new HeatDataPoint(i, j));
            }
        }
        return null;
    }

}
