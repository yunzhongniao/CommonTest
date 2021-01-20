package org.yunzhong.CommonTest.library.dao;

import org.yunzhong.CommonTest.library.model.HongBook;

public interface HongBookMapper {
    int insert(HongBook record);

    int insertSelective(HongBook record);
}