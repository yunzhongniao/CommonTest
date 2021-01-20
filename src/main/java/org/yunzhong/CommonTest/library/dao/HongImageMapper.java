package org.yunzhong.CommonTest.library.dao;

import org.yunzhong.CommonTest.library.model.HongImage;

public interface HongImageMapper {
    int insert(HongImage record);

    int insertSelective(HongImage record);
}