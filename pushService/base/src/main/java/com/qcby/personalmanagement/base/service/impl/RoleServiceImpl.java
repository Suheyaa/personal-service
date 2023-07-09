package com.qcby.personalmanagement.base.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.base.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.base.dto.RoleQuery;
import com.qcby.personalmanagement.base.mapper.RoleMapper;
import com.qcby.personalmanagement.base.po.RoleBusinessPO;
import com.qcby.personalmanagement.base.po.RolePO;
import com.qcby.personalmanagement.base.service.IRoleService;
import com.qcby.personalmanagement.base.service.RoleBusinessService;
import com.qcby.personalmanagement.base.vo.BusinessVO;
import com.qcby.personalmanagement.base.vo.RoleVO;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    public Integer batchDeletion(List<Long> ids) {
        // 删除role表数据
        boolean removeRole = this.removeByIds(ids);
        // 级联删除该角色在role_business对应的权限数据
        Integer removeRoleBusiness = roleBusinessService.bactchDeleteByRoleIds(ids);
        return removeRole ? 1 : 0;
    }

    @Override
    public List<RoleVO> pagingQuery(RoleQuery roleQuery) {
        Page<RolePO> page = new Page<>(roleQuery.getPageIndex(), roleQuery.getPageSize());
        QueryWrapper<RolePO> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(roleQuery.getRoleName())) {
            queryWrapper.like("role_name", roleQuery.getRoleName());
        }
        if (ObjectUtil.isNotNull(roleQuery.getPermission())) {
            queryWrapper.eq("permission", roleQuery.getPermission());
        }
        if (ObjectUtil.isNotNull(roleQuery.getStatus())) {
            queryWrapper.eq("status", roleQuery.getStatus());
        }
        if (ObjectUtil.isNotNull(roleQuery.getCreateTimeEnd()) && ObjectUtil.isNotNull(roleQuery.getCreateTimeEnd())) {
            String formatStart = roleQuery.getCreateTimeStart().format(DateTimeFormatter.ISO_DATE_TIME);
            String formatEnd = roleQuery.getCreateTimeEnd().format(DateTimeFormatter.ISO_DATE_TIME);
            queryWrapper.ge("create_time", formatStart);
            queryWrapper.le("create_time", formatEnd);
        }
        // 得到对应的po
        List<RolePO> rolePOS = baseMapper.selectPage(page, queryWrapper).getRecords();
        // 所有角色的id
        List<Long> ids = rolePOS.stream().map(RolePO::getId).collect(Collectors.toList());
        // 得到这些角色对应的权限id，然后转为map方便后面使用
        List<RoleBusinessPO> roleBusinessPOS = roleBusinessService.queryBusinessByRoleIds(ids);
        Map<Long,List<Long>> map = new HashMap<>();
        roleBusinessPOS.forEach(po->{
            map.computeIfAbsent(po.getRoleId(), k -> new ArrayList<>()).add(po.getBusinessId());
        });
        // po转vo并把对应的business_id塞进去
        return rolePOS.stream().map(po->{
           RoleVO roleVO = new RoleVO();
           BeanUtils.copyProperties(po, roleVO);
           roleVO.setIds(map.get(po.getId()));
           return roleVO;
        }).collect(Collectors.toList());
        // 两次查表，不写mapper.xml
    }


    @Override
    public Boolean changeStatus(Long id, Integer status) {
        RolePO rolePO = new RolePO();
        rolePO.setId(id);
        rolePO.setStatus(status);
        return this.updateById(rolePO);
    }

    @Override
    public List<Long> queryBusinessByRoleId(Long roleId) {
        return roleBusinessService.queryBusinessByRoleId(roleId);
    }

    @Override
    public Boolean export(List<Long> ids) throws IOException {
        List<RolePO> rolePOS = this.listByIds(ids);
        // 将PO转为VO最后化为list
        List<RoleVO> collect = rolePOS.stream().map((RolePO) -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(RolePO, roleVO);
//            roleVO.setIds(roleBusinessService.queryBusinessByRoleId(roleVO.getId()));
            return roleVO;
        }).collect(Collectors.toList());
        ArrayList<RoleVO> arrayList = new ArrayList<>(collect);
        String fileName = System.getProperty("user.dir") + "\\base\\src\\main\\resources\\角色表" + System.currentTimeMillis() + ".xlsx";
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                System.out.println("无法创建路径。");
            }
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        EasyExcel.write(fileName, RoleVO.class).sheet("模板").doWrite(arrayList);
        return true;
    }


}
