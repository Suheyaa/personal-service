package com.qcby.personalmanagement.base.service.impl;

import com.qcby.personalmanagement.base.entity.SysDept;
import com.qcby.personalmanagement.base.mapper.SysDeptMapper;
import com.qcby.personalmanagement.base.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    public boolean deleteDeptById(Long id) {
        int result = deptMapper.deleteDeptById(id);
        return result > 0;
    }

    @Override
    public boolean checkDeptExistUser(Long id) {
        int result = deptMapper.checkDeptExistUser(id);
        return result > 0;
    }

    @Override
    public boolean hasChildByDeptId(Long id) {
        int result = deptMapper.hasChildByDeptId(id);
        return result > 0;
    }

    @Override
    public SysDept selectDeptById(Long id) {
        return deptMapper.selectDeptById(id);
    }

    @Override
    public boolean insertDept(SysDept dept) {
        int result = deptMapper.insertDept(dept);
        return result > 0;
    }

    @Override
    public boolean updateDept(SysDept dept) {
        int result = deptMapper.updateDept(dept);
        return result > 0;
    }
}
