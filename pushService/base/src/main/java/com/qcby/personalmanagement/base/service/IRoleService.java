package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.base.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.base.param.RoleQueryParam;
import com.qcby.personalmanagement.base.po.RolePO;
import com.qcby.personalmanagement.base.vo.RoleVO;

import java.util.List;

public interface IRoleService extends IService<RolePO> {
    public Integer insertRole(RoleAndBusinessDTO roleAndBusinessDTO);
    public Integer updateRole(RoleAndBusinessDTO roleAndBusinessDTO);
    public Integer deleteRole(Long id);
    public Integer batchDeletion(List<Long> ids);
    public List<RoleVO> pagingQuery(RoleQueryParam roleQueryParam);

    public Boolean changeStatus(Long id, Integer status);
}
