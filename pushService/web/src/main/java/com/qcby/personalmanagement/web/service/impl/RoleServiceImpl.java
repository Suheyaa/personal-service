package com.qcby.personalmanagement.web.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.web.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.web.dto.RoleDTO;
import com.qcby.personalmanagement.web.mapper.RoleBusinessMapper;
import com.qcby.personalmanagement.web.mapper.RoleMapper;
import com.qcby.personalmanagement.web.param.RoleQueryParam;
import com.qcby.personalmanagement.web.po.BusinessPO;
import com.qcby.personalmanagement.web.po.RoleBusinessPO;
import com.qcby.personalmanagement.web.po.RolePO;
import com.qcby.personalmanagement.web.service.IRoleService;
import com.qcby.personalmanagement.web.service.RoleBusinessService;
import com.qcby.personalmanagement.web.vo.BusinessVO;
import com.qcby.personalmanagement.web.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements IRoleService {
    @Resource
    private RoleBusinessService roleBusinessService;


    @Override
    public Integer insertRole(RoleAndBusinessDTO roleAndBusinessDTO) {
        // 先在role表插入
        RolePO rolePO = new RolePO();
        rolePO.setRoleName(roleAndBusinessDTO.roleName);
        rolePO.setPermission(roleAndBusinessDTO.permission);
        rolePO.setStatus(roleAndBusinessDTO.status);
        rolePO.setRemark(roleAndBusinessDTO.remark);
        rolePO.setCreateTime(LocalDateTime.now());
        rolePO.setUpdateTime(LocalDateTime.now());
        boolean saveRole = this.save(rolePO);
        // 得到雪花算法产生的id用以在role_business表插入数据
        Long roleId = rolePO.getId();
        // 在role_business表插入数据
        //     转为roleBusinessPO用于插入
        List<RoleBusinessPO> collect = roleAndBusinessDTO.getIds().stream().map(e -> {
            RoleBusinessPO roleBusinessPO = new RoleBusinessPO();
            roleBusinessPO.setRoleId(roleId);
            roleBusinessPO.setBusinessId(e);
            return roleBusinessPO;
        }).collect(Collectors.toList());
        boolean saveRoleBusiness = roleBusinessService.saveBatch(collect);
        return saveRole && saveRoleBusiness ? 1 : 0;
    }


    @Override
    public Integer updateRole(RoleAndBusinessDTO roleAndBusinessDTO) {
        Long roleId = roleAndBusinessDTO.getId();
        RolePO rolePO = new RolePO();
        rolePO.setId(roleAndBusinessDTO.id);
        rolePO.setRoleName(roleAndBusinessDTO.roleName);
        rolePO.setPermission(roleAndBusinessDTO.permission);
        rolePO.setStatus(roleAndBusinessDTO.status);
        rolePO.setRemark(roleAndBusinessDTO.remark);
        int updateRole = this.getBaseMapper().updateById(rolePO);
        // 如果传参没有传ids，则不需要更新role_business表
        if (roleAndBusinessDTO.getIds().isEmpty() || roleAndBusinessDTO.getIds() == null) {
            return updateRole;
        }
        // 先删去已有数据再添加他的权限达成修改的目的
        roleBusinessService.deleteByRoleId(roleAndBusinessDTO.getId());
        List<RoleBusinessPO> collect = roleAndBusinessDTO.getIds().stream().map(e -> {
            RoleBusinessPO roleBusinessPO = new RoleBusinessPO();
            roleBusinessPO.setRoleId(roleId);
            roleBusinessPO.setBusinessId(e);
            return roleBusinessPO;
        }).collect(Collectors.toList());
        boolean saveBatch = roleBusinessService.saveBatch(collect);
        return updateRole > 0 && saveBatch ? 1 : 0;
    }

    @Override
    public Integer deleteRole(Long id) {
        // 删除role表数据
        RolePO rolePO = new RolePO();
        rolePO.setId(id);
        rolePO.setDeleteFlag(1);
        boolean removeRole = this.removeById(rolePO);
        // 级联删除该角色在role_business对应的权限数据
        Integer removeRoleBusiness = roleBusinessService.deleteByRoleId(id);
        // todo: 考虑如下情况，该角色没有任何权限，所以在role_business删除数据返回为0，但其实删除逻辑是正常的应返回1
        //return removeRoleBusiness > 0 && removeRole ? 1 : 0;
        return removeRole ? 1 : 0;

    }

    @Override
    public Integer batchDeletion(@RequestBody List<Long> ids) {
        // 删除role表数据
        boolean removeRole = this.removeByIds(ids);
        // 级联删除该角色在role_business对应的权限数据
        Integer removeRoleBusiness = roleBusinessService.bactchDeleteByRoleIds(ids);
        return removeRole ? 1 : 0;
    }

    @Override
    public List<RoleVO> pagingQuery(RoleQueryParam roleQueryParam) {
        Page<RolePO> page = new Page<>(roleQueryParam.getPageIndex(), roleQueryParam.getPageSize());
        QueryWrapper<RolePO> queryWrapper = Wrappers.emptyWrapper();
        if (ObjectUtil.isNotNull(roleQueryParam.getRoleName())) {
            queryWrapper.like("roleName", roleQueryParam.getRoleName());
        }
        if (ObjectUtil.isNotNull(roleQueryParam.getPermission())) {
            queryWrapper.eq("permission", roleQueryParam.getPermission());
        }
        if (ObjectUtil.isNotNull(roleQueryParam.getStatus())) {
            queryWrapper.eq("status", roleQueryParam.getStatus());
        }
        if (ObjectUtil.isNotNull(roleQueryParam.getCreateTimeEnd()) && ObjectUtil.isNotNull(roleQueryParam.getCreateTimeEnd())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatStart = sdf.format(roleQueryParam.getCreateTimeStart());
            String formatEnd = sdf.format(roleQueryParam.getCreateTimeEnd());
            queryWrapper.ge("createTime",formatStart);
            queryWrapper.le("createTime",formatEnd);
        }
        // 将PO转为VO最后化为list
        return baseMapper.selectPage(page, queryWrapper).convert((RolePO) -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(RolePO, roleVO);
            return roleVO;
        }).getRecords();
    }


    @Override
    public Boolean changeStatus(Long id, Integer status) {
        RolePO rolePO = new RolePO();
        rolePO.setId(id);
        rolePO.setStatus(status);
        return this.updateById(rolePO);
    }

}
