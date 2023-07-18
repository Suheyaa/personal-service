package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.DeptDTO;
import com.qcby.personalmanagement.base.service.IDeptService;
import com.qcby.personalmanagement.base.vo.DeptVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private IDeptService deptService;

    /**
     * 添加部门
     * @param deptDTO
     * @return
     */
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody DeptDTO deptDTO){
        return Result.getSuccessResult(deptService.insert(deptDTO));
    }

    @GetMapping("/delete")
    public Result<Integer> delete(Long id){
        return Result.getSuccessResult(deptService.delete(id));
    }

    @PostMapping("/update")
    public Result<Integer> update(@RequestBody DeptDTO deptDTO){
        return Result.getSuccessResult(deptService.update(deptDTO));
    }

    @GetMapping("/list")
    public Result<List<DeptVO>> list(DeptDTO deptDTO){
        return Result.getSuccessResult(deptService.list(deptDTO));
    }

    @GetMapping("/selectDeptById")
    public Result<DeptVO> selectDeptById(Long id){
        return Result.getSuccessResult(deptService.selectDeptById(id));
    }

}
