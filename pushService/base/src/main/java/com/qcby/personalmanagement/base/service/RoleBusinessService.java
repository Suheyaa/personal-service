package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.base.po.RoleBusinessPO;

import java.util.List;

public interface RoleBusinessService extends IService<RoleBusinessPO> {
    public Integer deleteByRoleId(Long roleId);
    public Integer bactchDeleteByRoleIds(List<Long> ids);
}
