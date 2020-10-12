package org.yunzhong.CommonTest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DatacenterSqlParamDef extends DatacenterParamDef {

    private String sql;
}
