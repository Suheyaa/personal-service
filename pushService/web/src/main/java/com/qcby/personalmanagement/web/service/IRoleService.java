package com.qcby.personalmanagement.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.web.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.web.dto.RoleDTO;
import com.qcby.personalmanagement.web.param.RoleQueryParam;
import com.qcby.personalmanagement.web.po.RolePO;
import com.qcby.personalmanagement.web.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRoleService extends IService<RolePO> {
    public Integer insertRole(RoleAndBusinessDTO roleAndBusinessDTO);
    public Integer updateRole(RoleAndBusinessDTO roleAndBusinessDTO);
    public Integer deleteRole(Long id);
    public Integer batchDeletion(List<Long> ids);
    public List<RoleVO> pagingQuery(RoleQueryParam roleQueryParam);

    public Boolean changeStatus(Long id, Integer status);
}
