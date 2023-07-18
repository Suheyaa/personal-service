package com.qcby.personalmanagement.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcby.personalmanagement.base.dto.DeptDTO;
import com.qcby.personalmanagement.base.po.DeptPO;
import com.qcby.personalmanagement.base.vo.DeptVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<DeptPO> {
    /**
     * 查询部门是否存在用户
     * @param id
     * @return
     */
    public int userByDeptid(long id);

    /**
     * 根据id查询部门信息
     * @param id
     * @return
     */
    public DeptVO selectDeptById(Long id);
}
