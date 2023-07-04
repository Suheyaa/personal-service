package com.qcby.personalmanagement.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.web.dto.BusinessDTO;
import com.qcby.personalmanagement.web.mapper.BusinessMapper;
import com.qcby.personalmanagement.web.mapper.RoleBusinessMapper;
import com.qcby.personalmanagement.web.po.BusinessPO;
import com.qcby.personalmanagement.web.service.IBusinessService;
import com.qcby.personalmanagement.web.vo.BusinessVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, BusinessPO> implements IBusinessService {

    @Resource
    RoleBusinessMapper roleBusinessMapper;


    @Override
    public Integer insert(BusinessDTO businessDTO) {
        // 将DTO转为PO
        BusinessPO businessPO = new BusinessPO();
        businessPO.setBusinessName(businessDTO.getBusinessName());
        businessPO.setPermission(businessDTO.getPermission());
        businessPO.setPath(businessDTO.getPath());
        businessPO.setIcon(businessDTO.getIcon());
        businessPO.setStatus(businessDTO.getStatus());
        // 两个时间默认为当前时间
        businessPO.setCreateTime(LocalDateTime.now());
        businessPO.setUpdateTime(LocalDateTime.now());
        return this.getBaseMapper().insert(businessPO);
    }

    @Override
    public Integer delete(Long id) {
        LambdaUpdateWrapper<BusinessPO> luw = new LambdaUpdateWrapper<>();
        luw.eq(BusinessPO::getId, id).set(BusinessPO::getDeleteFlag, 1);
        return this.getBaseMapper().update(null, luw);
    }

    @Override
    public Integer update(BusinessDTO businessDTO) {
        // DTO -> PO
        BusinessPO businessPO = new BusinessPO();
        businessPO.setId(businessDTO.getId());
        businessPO.setStatus(businessDTO.getStatus());
        businessPO.setBusinessName(businessDTO.getBusinessName());
        businessPO.setPermission(businessDTO.getPermission());
        businessPO.setPath(businessDTO.getPath());
        businessPO.setIcon(businessDTO.getIcon());
        return this.getBaseMapper().updateById(businessPO);
    }

    @Override
    public List<BusinessVO> Paging_query(@Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize, BusinessDTO businessDTO) {
        Page<BusinessPO> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<BusinessPO> queryWrapper = Wrappers.emptyWrapper();
        if (ObjectUtil.isNotNull(businessDTO.businessName)) {
            queryWrapper.like("business_name", businessDTO.businessName);
        }
        if (ObjectUtil.isNotNull(businessDTO.status)) {
            queryWrapper.eq("status", businessDTO.status);
        }
        // 将PO转为VO最后化为list
        return baseMapper.selectPage(page, queryWrapper).convert((businessPO) -> {
            BusinessVO businessVO = new BusinessVO();
            BeanUtils.copyProperties(businessPO, businessVO);
            return businessVO;
        }).getRecords();

    }


}

























