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
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,deptPO1.getSuperiorId())
                .eq(DeptPO::getOrderNum,deptPO1.getOrderNum());
        DeptVO deptVO = deptMapper.selectDeptById(deptPO1.getSuperiorId());
        DeptPO deptPO2 = this.getBaseMapper().selectOne(lqw);
        DeptPO deptPO3 = this.getBaseMapper().selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(deptPO2)){
            throw new ServiceException("500","该部门已存在");
        }
        if (ObjectUtil.isNotNull(deptPO3)){
            throw new ServiceException("500","该排序已存在部门");
        }
        DeptPO deptPO = new DeptPO();
        deptPO.setDeptName(deptPO1.getDeptName());
        deptPO.setLeaderId(deptPO1.getLeaderId());
        deptPO.setDeptStatus(deptPO1.getDeptStatus());
        deptPO.setAncestors(deptPO1.getAncestors());
        deptPO.setSuperiorId(deptPO1.getSuperiorId());
        deptPO.setOrderNum(deptPO1.getOrderNum());
        deptPO.setAncestors(deptVO.getAncestors() + "," + deptPO1.getSuperiorId());
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
        DeptVO deptVO = deptMapper.selectDeptById(deptPO1.getSuperiorId());
        LambdaQueryWrapper<DeptPO> lqw = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,deptPO1.getSuperiorId())
                .eq(DeptPO::getDeptName,deptPO1.getDeptName())
                .ne(DeptPO::getId,deptPO1.getId());
        LambdaQueryWrapper<DeptPO> queryWrapper = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,deptPO1.getSuperiorId())
                .eq(DeptPO::getOrderNum,deptPO1.getOrderNum())
                .ne(DeptPO::getId,deptPO1.getId());
        DeptPO deptPO2 = this.getBaseMapper().selectOne(lqw);
        DeptPO deptPO3 = this.getBaseMapper().selectOne(queryWrapper);
        if(deptPO1.getId().equals(deptPO1.getSuperiorId())){
            throw new ServiceException("500","上级部门不能是自己");
        }
        if (ObjectUtil.isNotNull(deptPO2)){
            throw new ServiceException("500","该部门已存在");
        }
        if (ObjectUtil.isNotNull(deptPO3)){
            throw new ServiceException("500","该排序已存在部门");
        }
        DeptPO deptPO = new DeptPO();
        deptPO.setId(deptDTO.getId());
        deptPO.setDeptName(deptPO1.getDeptName());
        deptPO.setLeaderId(deptPO1.getLeaderId());
        deptPO.setDeptStatus(deptPO1.getDeptStatus());
        deptPO.setSuperiorId(deptPO1.getSuperiorId());
        deptPO.setOrderNum(deptPO1.getOrderNum());
        deptPO.setAncestors(deptVO.getAncestors() + "," + deptPO1.getSuperiorId());
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
//            if(deptDTO.getDeptStatus()==1){
//                List<DeptVO> deptVOS = buildDeptTree();
//                return deptVOS;
//            }
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
    public List<DeptVO> buildDeptTree() {
        LambdaQueryWrapper<DeptPO> lambdaQueryWrapper = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,0);
        DeptPO deptPO = deptMapper.selectOne(lambdaQueryWrapper);
        DeptVO deptVO = BeanUtil.copyProperties(deptPO, DeptVO.class);
        buildChildren(deptVO);
        List<DeptVO> deptVOS = new ArrayList<>();
        deptVOS.add(deptVO);

        return deptVOS;
    }

    public void buildChildren(DeptVO superDept){
        List<DeptVO>deptVOS = new ArrayList<>();
        //查询条件
        LambdaQueryWrapper<DeptPO>lambdaQueryWrapper = new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,superDept.getId())
                .eq(DeptPO::getDeleteFlag,0);
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
