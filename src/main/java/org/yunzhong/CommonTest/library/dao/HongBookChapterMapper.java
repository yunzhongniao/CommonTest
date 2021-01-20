package org.yunzhong.CommonTest.library.dao;

import org.yunzhong.CommonTest.library.model.HongBookChapter;

public interface HongBookChapterMapper {
    int insert(HongBookChapter record);

    int insertSelective(HongBookChapter record);
}