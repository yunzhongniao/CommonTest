package org.yunzhong.CommonTest.library.dao;

import org.yunzhong.CommonTest.library.model.SysDept;

public interface SysDeptMapper {
    int insert(SysDept record);

    int insertSelective(SysDept record);
}