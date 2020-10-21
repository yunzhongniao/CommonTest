package org.yunzhong.CommonTest.persistance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.yunzhong.CommonTest.model.DatacenterParamDef;
import org.yunzhong.CommonTest.persistance.dao.DatacenterParamSqlMapper;

@Service
public class DataCenterService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DatacenterParamSqlMapper paramSqlMapper;

    public Map<String, Object> initData() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<DatacenterParamDef> selectParamDefs = paramSqlMapper.selectParamDefs();
        if (selectParamDefs != null) {
            for (DatacenterParamDef datacenterParamDef : selectParamDefs) {
                Object data = null;
                result.put(datacenterParamDef.getKey(), data);
            }
        }
        return result;
    }
}
