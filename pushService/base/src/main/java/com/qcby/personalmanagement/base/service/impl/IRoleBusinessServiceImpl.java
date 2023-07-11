package com.qcby.personalmanagement.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.base.mapper.RoleBusinessMapper;
import com.qcby.personalmanagement.base.po.RoleBusinessPO;
import com.qcby.personalmanagement.base.service.IRoleBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IRoleBusinessServiceImpl extends ServiceImpl<RoleBusinessMapper, RoleBusinessPO> implements IRoleBusinessService {

    @Resource
    RoleBusinessMapper roleBusinessMapper;

    @Override
    public Integer deleteByRoleId(Long roleId) {
        return roleBusinessMapper.deleteByRoleId(roleId);
    }

    @Override
    public Integer bactchDeleteByRoleIds(List<Long> roleIds) {
        return roleBusinessMapper.bactchDeleteByRoleIds(roleIds);
    }

    @Override
    public List<Long> queryBusinessByRoleId(Long roleId) {
        QueryWrapper<RoleBusinessPO> qw = new QueryWrapper<>();
        qw.eq("role_id",roleId);
        return this.list(qw).stream().map(e->{
            return e.getBusinessId();
        }).collect(Collectors.toList());
    }
    @Override
    public List<RoleBusinessPO> queryBusinessByRoleIds(List<Long> ids) {
        QueryWrapper<RoleBusinessPO> qw = new QueryWrapper<>();
        qw.in("role_id", ids);
        return this.list(qw);
    }

}
