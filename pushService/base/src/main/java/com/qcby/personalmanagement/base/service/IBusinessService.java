package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.base.dto.BusinessDTO;
import com.qcby.personalmanagement.base.po.BusinessPO;
import com.qcby.personalmanagement.base.vo.BusinessVO;

import java.io.IOException;
import java.util.List;

public interface IBusinessService extends IService<BusinessPO> {
    public Integer insert(BusinessDTO businessDTO);
    public Integer delete(Long id);
    public Integer update(BusinessDTO businessDTO);
    List<BusinessVO> list(Integer pageIndex, Integer pageSize, BusinessDTO businessDTO);
    public String export(List<Long> ids) throws IOException;
}
