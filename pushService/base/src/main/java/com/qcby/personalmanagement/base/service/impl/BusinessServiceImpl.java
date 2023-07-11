package com.qcby.personalmanagement.base.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.base.dto.BusinessDTO;
import com.qcby.personalmanagement.base.mapper.BusinessMapper;
import com.qcby.personalmanagement.base.po.BusinessPO;
import com.qcby.personalmanagement.base.po.RoleBusinessPO;
import com.qcby.personalmanagement.base.po.RolePO;
import com.qcby.personalmanagement.base.service.IBusinessService;
import com.qcby.personalmanagement.base.vo.BusinessExcelVO;
import com.qcby.personalmanagement.base.vo.BusinessVO;
import com.qcby.personalmanagement.base.vo.RoleExcelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, BusinessPO> implements IBusinessService {
    String path="E:\\src\\";

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
    public List<BusinessVO> list(Integer pageIndex, Integer pageSize, BusinessDTO businessDTO) {
        Page<BusinessPO> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<BusinessPO> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(businessDTO.businessName)) {
            queryWrapper.like("business_name", businessDTO.businessName);
        }
        if (ObjectUtil.isNotNull(businessDTO.status)) {
            queryWrapper.eq("status", businessDTO.status);
        }
        queryWrapper.eq("delete_flag", 0);
        // 将PO转为VO最后化为list
        return baseMapper.selectPage(page, queryWrapper).convert((businessPO) -> {
            BusinessVO businessVO = new BusinessVO();
            BeanUtils.copyProperties(businessPO, businessVO);
            return businessVO;
        }).getRecords();
    }
    @Override
    public String export(List<Long> ids) throws IOException{
        // 得到要导出的数据
        ArrayList<BusinessExcelVO> arrayList = this.listByIds(ids).stream().map((e) -> {
            BusinessExcelVO businessExcelVO = new BusinessExcelVO();
            BeanUtils.copyProperties(e, businessExcelVO);
            return businessExcelVO;
        }).collect(Collectors.toCollection(ArrayList::new));


        // 导出数据
        // 生成路径及文件名
        String fileName = path + System.currentTimeMillis() + ".xlsx";
        // 判断父路径文件夹是否存在，不存在则创建
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        // 判断文件是否存在，不存在则创建
        if (!file.exists()) {
            file.createNewFile();
        }
        EasyExcel.write(fileName, BusinessExcelVO.class).sheet("业务").doWrite(arrayList);
        return fileName;
    }
}
