package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.BusinessDTO;
import com.qcby.personalmanagement.base.po.BusinessPO;
import com.qcby.personalmanagement.base.service.IBusinessService;
import com.qcby.personalmanagement.base.vo.BusinessVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Resource
    private IBusinessService businessService;

    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.insert(businessDTO));
    }

    @PostMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        return Result.getSuccessResult(businessService.delete(id));
    }

    @PostMapping("/update")
    public Result<Integer> update(@RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.update(businessDTO));
    }

    @PostMapping("/list/{pageIndex}/{pageSize}")
    public Result<List<BusinessVO>> list(@PathVariable Integer pageIndex, @PathVariable Integer pageSize, @RequestBody BusinessDTO businessDTO) {
        return Result.getSuccessResult(businessService.list(pageIndex, pageSize, businessDTO));
    }

    @GetMapping("/getCounts")
    public Result<Integer> getCounts() {
        return Result.getSuccessResult(businessService.count());
    }

    @GetMapping("/selectById/{id}")
    public Result<BusinessVO> getById(@PathVariable Long id) {
        BusinessPO businessPo = this.businessService.getById(id);
        BusinessVO businessVO = new BusinessVO();
        businessVO.setId(businessPo.getId());
        businessVO.setStatus(businessPo.getStatus());
        businessVO.setBusinessName(businessPo.getBusinessName());
        businessVO.setPermission(businessPo.getPermission());
        businessVO.setPath(businessPo.getPath());
        businessVO.setIcon(businessPo.getIcon());
        businessVO.setCreateTime(businessPo.getCreateTime());
        return Result.getSuccessResult(businessVO);
    }
    @GetMapping("/list_all")
    public Result<List<BusinessVO>> listAll() {
        List<BusinessPO> list = businessService.list();
        List<BusinessVO> collect = list.stream().map((businessPO) -> {
            BusinessVO businessVO = new BusinessVO();
            BeanUtils.copyProperties(businessPO, businessVO);
            return businessVO;
        }).collect(Collectors.toList());
        return Result.getSuccessResult(collect);
    }
}
