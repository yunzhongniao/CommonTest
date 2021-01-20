package org.yunzhong.CommonTest.library.dao;

import org.yunzhong.CommonTest.library.model.SysUser;

public interface SysUserMapper {
    int insert(SysUser record);

    int insertSelective(SysUser record);
}