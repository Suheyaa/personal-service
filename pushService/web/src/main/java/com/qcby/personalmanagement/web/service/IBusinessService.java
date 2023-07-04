package com.qcby.personalmanagement.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.web.dto.BusinessDTO;
import com.qcby.personalmanagement.web.po.BusinessPO;
import com.qcby.personalmanagement.web.vo.BusinessVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBusinessService extends IService<BusinessPO> {
    public Integer insert(BusinessDTO businessDTO);
    public Integer delete(Long id);
    public Integer update(BusinessDTO businessDTO);

    List<BusinessVO> Paging_query(@Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize, BusinessDTO businessDTO);
}
