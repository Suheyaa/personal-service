package com.qcby.personalmanagement.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcby.personalmanagement.web.po.RoleBusinessPO;

import java.util.List;

public interface RoleBusinessMapper extends BaseMapper<RoleBusinessPO> {

    public Integer deleteByRoleId(Long roleId);

    public Integer bactchDeleteByRoleIds(List<Long> roleId);


}
