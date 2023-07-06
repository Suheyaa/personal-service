package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.BusinessDTO;
import com.qcby.personalmanagement.base.po.BusinessPO;
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
    public Result<List<BusinessVO>> pagingQuery(@PathVariable Integer pageIndex, @PathVariable Integer pageSize, @RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.Paging_query(pageIndex, pageSize, businessDTO));
    }

    @RequestMapping("/get_counts")
    public Result<Integer> getCounts() {
        return Result.getSuccessResult(businessService.count());
    }

    @RequestMapping("/get_by_id")
    public Result<BusinessVO> getById(Integer id) {
        BusinessPO businessPO = this.businessService.getById(id);
        BusinessVO businessVO = new BusinessVO();
        businessVO.setId(businessPO.getId());
        businessVO.setStatus(businessPO.getStatus());
        businessVO.setBusinessName(businessPO.getBusinessName());
        businessVO.setPermission(businessPO.getPermission());
        businessVO.setPath(businessPO.getPath());
        businessVO.setIcon(businessPO.getIcon());
        businessVO.setCreateTime(businessPO.getCreateTime());
        return Result.getSuccessResult(businessVO);
    }
}


















