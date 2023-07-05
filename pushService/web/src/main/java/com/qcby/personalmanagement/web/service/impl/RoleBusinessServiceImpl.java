package com.qcby.personalmanagement.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.web.mapper.RoleBusinessMapper;
import com.qcby.personalmanagement.web.po.RoleBusinessPO;
import com.qcby.personalmanagement.web.service.RoleBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleBusinessServiceImpl extends ServiceImpl<RoleBusinessMapper, RoleBusinessPO> implements RoleBusinessService {

    @Resource
    RoleBusinessMapper roleBusinessMapper;

    @Override
    public Integer deleteByRoleId(Long roleId){
        return roleBusinessMapper.deleteByRoleId(roleId);
    }
    @Override
    public Integer bactchDeleteByRoleIds(List<Long> roleIds){
        return roleBusinessMapper.bactchDeleteByRoleIds(roleIds);
    }

}
