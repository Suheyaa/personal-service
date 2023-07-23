package com.qcby.personalmanagement.base.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.framework.common.exception.ServiceException;
import com.qcby.personalmanagement.base.dto.DeptDTO;
import com.qcby.personalmanagement.base.mapper.DeptMapper;
import com.qcby.personalmanagement.base.po.DeptPO;
import com.qcby.personalmanagement.base.service.IDeptService;
import com.qcby.personalmanagement.base.vo.DeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptPO> implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(DeptDTO deptDTO) {
        DeptPO deptPO1 = BeanUtil.copyProperties(deptDTO, DeptPO.class);
        LambdaQueryWrapper<DeptPO> lqw = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,deptPO1.getSuperiorId())
                .eq(DeptPO::getDeptName,deptPO1.getDeptName());
        DeptPO deptPO2 = this.getBaseMapper().selectOne(lqw);
        if (ObjectUtil.isNotNull(deptPO2)){
            throw new ServiceException("500","该部门已存在");
        }
        DeptPO deptPO = new DeptPO();
        deptPO.setDeptName(deptDTO.getDeptName());
        deptPO.setLeaderId(deptDTO.getLeaderId());
        deptPO.setDeptStatus(deptDTO.getDeptStatus());
        deptPO.setAncestors(deptDTO.getAncestors());
        deptPO.setSuperiorId(deptDTO.getSuperiorId());
        deptPO.setOrderNum(deptDTO.getOrderNum());
        return this.getBaseMapper().insert(deptPO);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long id) {
        LambdaQueryWrapper<DeptPO> lqw = new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getSuperiorId,id);
        List list = this.getBaseMapper().selectList(lqw);
        int userCount = deptMapper.userByDeptid(id);
        if (list.size() != 0) {
            throw new ServiceException("500","该部门存在下级部门");
        }
        if(userCount != 0){
            throw new ServiceException("500","该部门存在用户");
        }
        LambdaQueryWrapper<DeptPO> luw  = new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getId,id);
        return this.getBaseMapper().delete(luw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer userByDeptid(long id) {
        return deptMapper.userByDeptid(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(DeptDTO deptDTO) {
        DeptPO deptPO1 = BeanUtil.copyProperties(deptDTO, DeptPO.class);
        LambdaQueryWrapper<DeptPO> lqw = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,deptPO1.getSuperiorId())
                .eq(DeptPO::getDeptName,deptPO1.getDeptName());
        List list = this.getBaseMapper().selectList(lqw);
        if (list.size() != 0) {
            throw new ServiceException("500","部门名称已存在");
        }
        if(deptPO1.getId().equals(deptPO1.getSuperiorId())){
            throw new ServiceException("500","上级部门不能是自己");
        }
        DeptPO deptPO = new DeptPO();
        deptPO.setId(deptDTO.getId());
        deptPO.setDeptName(deptDTO.getDeptName());
        deptPO.setLeaderId(deptDTO.getLeaderId());
        deptPO.setDeptStatus(deptDTO.getDeptStatus());
        deptPO.setAncestors(deptDTO.getAncestors());
        deptPO.setSuperiorId(deptDTO.getSuperiorId());
        deptPO.setOrderNum(deptDTO.getOrderNum());
        return this.getBaseMapper().updateById(deptPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeptVO> list(DeptDTO deptDTO) {
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<DeptPO>();
        if(ObjectUtil.isNotNull(deptDTO.getDeptName())){
            queryWrapper.like(DeptPO::getDeptName,deptDTO.getDeptName());
        }
        if(ObjectUtil.isNotNull(deptDTO.getDeptStatus())){
            queryWrapper.eq(DeptPO::getDeptStatus,deptDTO.getDeptStatus());
        }
        queryWrapper.eq(DeptPO::getDeleteFlag, 0)
                .orderByAsc(DeptPO::getSuperiorId)
                .orderByAsc(DeptPO::getOrderNum);
        List<DeptPO> deptPOS = this.getBaseMapper().selectList(queryWrapper);
        List<DeptVO> deptVOS = BeanUtil.copyToList(deptPOS, DeptVO.class);
        return deptVOS;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptVO selectDeptById(Long id) {
        DeptVO deptVO = deptMapper.selectDeptById(id);
        return deptVO;
    }

    @Override
    public List<DeptVO> userList() {
        return deptMapper.userList();
    }

    @Override
    public DeptVO buildDeptTree() {
        LambdaQueryWrapper<DeptPO> lambdaQueryWrapper = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,0);
        DeptPO deptPO = deptMapper.selectOne(lambdaQueryWrapper);
        DeptVO deptVO = BeanUtil.copyProperties(deptPO, DeptVO.class);
        buildChildren(deptVO);

        return deptVO;
    }

    public void buildChildren(DeptVO superDept){
        List<DeptVO>deptVOS = new ArrayList<>();
        //查询条件
        LambdaQueryWrapper<DeptPO>lambdaQueryWrapper = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,superDept.getId());
        //查询上级部门对应的所有子集部门
        List<DeptPO> deptPOS = deptMapper.selectList(lambdaQueryWrapper);
        //查询结果为null
        if(CollectionUtil.isEmpty(deptPOS)){
            return ;
        }
        //将查询结果转成VO装进deptVOS
        for(DeptPO deptPO: deptPOS){
            DeptVO deptVO = BeanUtil.copyProperties(deptPO, DeptVO.class);
            deptVOS.add(deptVO);
        }
        //将子级部门赋给上级部门
        superDept.setChildren(deptVOS);
        //查询该子级部门的子级部门
        for(DeptVO deptVO :deptVOS){
            buildChildren(deptVO);
        }
    }


}
