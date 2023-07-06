package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.BusinessDTO;
import com.qcby.personalmanagement.base.service.IBusinessService;
import com.qcby.personalmanagement.base.vo.BusinessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Resource
    private IBusinessService businessService;

    @RequestMapping("/insert")
    public Result<Integer> insert(BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.insert(businessDTO));
    }

    @RequestMapping("/delete")
    public Result<Integer> delete(Long id) {
        return Result.getSuccessResult(businessService.delete(id));
    }

    @RequestMapping("/update")
    public Result<Integer> update(BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.update(businessDTO));
    }

    @RequestMapping("/paging_query")
    public Result<List<BusinessVO>> Paging_query(Integer pageIndex, Integer pageSize, BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.Paging_query(pageIndex,pageSize,businessDTO));
    }

}


















