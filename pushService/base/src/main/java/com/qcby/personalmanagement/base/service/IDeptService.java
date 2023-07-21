package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.personalmanagement.base.dto.DeptDTO;
import com.qcby.personalmanagement.base.po.DeptPO;
import com.qcby.personalmanagement.base.vo.DeptVO;

import java.util.List;

public interface IDeptService extends IService<DeptPO> {

    /**
     * 添加部门
     * @param deptDTO
     * @return
     */
    public Integer insert(DeptDTO deptDTO);

    /**
     * 删除部门
     * @param id
     * @return
     */
    public Integer delete(Long id);

    /**
     * 查询部门是否存在用户
     * @param id
     * @return
     */
    public Integer userByDeptid(long id);

    /**
     * 修改部门
     * @param deptDTO
     * @return
     */
    public Integer update(DeptDTO deptDTO);

    /**
     * 查询部门列表
     * @param deptDTO
     * @return
     */
    public List<DeptVO> list(DeptDTO deptDTO);

    /**
     * 根据id查询部门信息
     * @param id
     * @return
     */
    public DeptVO selectDeptById(Long id);

    /**
     * 添加部门时查询用户信息
     * @return
     */
    public List<DeptVO> userList();

}
