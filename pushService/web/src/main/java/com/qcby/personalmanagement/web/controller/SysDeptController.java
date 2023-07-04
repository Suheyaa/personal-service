package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.entity.SysDept;
import com.qcby.personalmanagement.base.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 删除部门
     */

    @GetMapping("/delete")
    public Result<String> deleteById(Long id){
        //存在下级部门
        if(deptService.hasChildByDeptId(id)){

        }
        //部门存在用户
        if(deptService.checkDeptExistUser(id)){

        }
        boolean code = deptService.deleteDeptById(id);
        return new Result<String>(0,code?"成功":"失败","200");
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
    public Result<String> insert(SysDept dept){
        boolean code = deptService.insertDept(dept);
        return new Result<String>(0,code?"成功":"失败","200");
    }

    /**
     * 修改部门
     */
    @PostMapping("/update")
    public Result<String> update(SysDept dept){
        boolean code = deptService.updateDept(dept);
        return new Result<String>(0,code?"成功":"失败","200");
    }

}
