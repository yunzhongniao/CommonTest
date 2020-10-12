package org.yunzhong.CommonTest.model;

import lombok.Data;

@Data
public class DatacenterParamDef {
    private String key;
    private String type;
    /**
     * 解析value的class，默认为null
     */
    private String parserClass;
}
