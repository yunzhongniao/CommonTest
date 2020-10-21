package org.yunzhong.CommonTest.persistance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.yunzhong.CommonTest.model.DatacenterParamDef;

@Mapper
public interface DatacenterParamSqlMapper {

    @Select(value = "select * from datacenter_param_def")
    @Results(id = "empMap", value = { @Result(column = "key", property = "key", id = true),
            @Result(column = "name", property = "name"), @Result(property = "referenceKey", column = "reference_key"),
            @Result(column = "sort", property = "sort"), @Result(column = "type", property = "type"),
            @Result(property = "showType", column = "show_type"), @Result(column = "sql", property = "sql"),
            @Result(property = "modelClass", column = "model_class"),
            @Result(property = "staticValue", column = "static_value") })
    public List<DatacenterParamDef> selectParamDefs();
}
