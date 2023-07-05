package com.qcby.personalmanagement.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcby.personalmanagement.base.po.RoleBusinessPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleBusinessMapper extends BaseMapper<RoleBusinessPO> {

    public Integer deleteByRoleId(Long roleId);

    public Integer bactchDeleteByRoleIds(List<Long> roleId);


}
