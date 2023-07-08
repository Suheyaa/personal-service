package com.qcby.personalmanagement.web.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.qcby.framework.common.exception.ServiceException;
import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.entity.SysDept;
import com.qcby.personalmanagement.base.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/list")
    public List<SysDept> list(SysDept dept){
        List<SysDept> depts = deptService.selectDeptList(dept);
        return depts;
    }

    /**
     * 查询部门列表（排除节点）
     */
    @GetMapping("/list/exclude/{id}")
    public List<SysDept> exclude(Long id){
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        List<SysDept> list = new ArrayList<>();
        for (SysDept dept : depts) {
            if (!dept.getId().equals(id)){
                list.add(dept);
            }
        }
        return list;
    }

    /**
     * 删除部门
     */

    @GetMapping("/delete")
    public Result<Boolean> deleteById(Long id){
        //存在下级部门
        if(deptService.hasChildByDeptId(id)){
            throw new ServiceException("500","存在下级部门，不允许删除");
        }
        //部门存在用户
        if(deptService.checkDeptExistUser(id)){

            throw new ServiceException("500","部门存在用户，不允许删除");
        }
        boolean code = deptService.deleteDeptById(id);
        return Result.getSuccessResult(code);
    }

    /**
     * 根据id查询部门信息
     */
    @GetMapping("/selectById")
    public SysDept selectById(Long id){
        SysDept dept = deptService.selectDeptById(id);
        return dept;
    }

    /**
     * 添加部门
     */
    @PostMapping("/insert")
    public Result<Boolean> insert(@RequestBody SysDept dept){
        if (!deptService.checkDeptNameUnique(dept)){
            return Result.getSuccessResult(Boolean.FALSE);
        }
        boolean code = deptService.insertDept(dept);
        return Result.getSuccessResult(code);
    }

    /**
     * 修改部门
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysDept dept){
        Long deptId = dept.getId();
        if (!deptService.checkDeptNameUnique(dept))
        {
            throw new ServiceException("500","部门名称已存在");
        }
        else if (dept.getSuperiorId().equals(deptId))
        {
            throw new ServiceException("500","上级部门不能是自己");
        }
        else if (deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            throw new ServiceException("500","该部门包含未停用的子部门");
        }
        boolean code = deptService.updateDept(dept);
        return Result.getSuccessResult(code);
    }

}
