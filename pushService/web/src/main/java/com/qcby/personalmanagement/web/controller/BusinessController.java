package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.BusinessDTO;
import com.qcby.personalmanagement.base.service.IBusinessService;
import com.qcby.personalmanagement.base.vo.BusinessVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Resource
    private IBusinessService businessService;

    @RequestMapping("/insert")
    public Result<Integer> insert(@RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.insert(businessDTO));
    }

    @RequestMapping("/delete")
    public Result<Integer> delete(@RequestBody Long id) {
        return Result.getSuccessResult(businessService.delete(id));
    }

    @RequestMapping("/update")
    public Result<Integer> update(@RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.update(businessDTO));
    }

    @PostMapping("/paging_query/{pageIndex}/{pageSize}")
    public Result<List<BusinessVO>> Paging_query(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.Paging_query(pageIndex,pageSize,businessDTO));
    }

}


















