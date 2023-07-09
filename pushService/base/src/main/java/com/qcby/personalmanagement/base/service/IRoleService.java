package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.base.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.base.dto.RoleQuery;
import com.qcby.personalmanagement.base.po.RolePO;
import com.qcby.personalmanagement.base.vo.RoleVO;

import java.io.IOException;
import java.util.List;

public interface IRoleService extends IService<RolePO> {
    public Integer insertRole(RoleAndBusinessDTO roleAndBusinessDTO);
    public Integer updateRole(RoleAndBusinessDTO roleAndBusinessDTO);
    public Integer deleteRole(Long id);
    public Integer batchDeletion(List<Long> ids);
    public List<RoleVO> pagingQuery(RoleQuery roleQuery);

    public Boolean changeStatus(Long id, Integer status);

    public List<Long> queryBusinessByRoleId(Long roleId);

    public Boolean export(List<Long> ids) throws IOException;
}
