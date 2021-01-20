package org.yunzhong.CommonTest.library.dao;

import org.yunzhong.CommonTest.library.model.HongBookReadHis;

public interface HongBookReadHisMapper {
    int insert(HongBookReadHis record);

    int insertSelective(HongBookReadHis record);
}