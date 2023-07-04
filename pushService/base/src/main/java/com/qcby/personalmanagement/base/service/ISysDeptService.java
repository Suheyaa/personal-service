package com.qcby.personalmanagement.base.service;

import com.qcby.personalmanagement.base.entity.SysDept;

import java.util.List;

public interface ISysDeptService {

    /**
     * 查询部门信息
     * @param dept
     * @return
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 删除部门
     * @param id
     * @return
     */
    public boolean deleteDeptById(Long id);

    /**
     * 检查部门是否存在用户
     * @param id
     * @return
     */
    public boolean checkDeptExistUser(Long id);

    /**
     * 检查部门是否存在下级部门
     * @param id
     * @return
     */
    public boolean hasChildByDeptId(Long id);


    /**
     * 根据id查询部门
     */
    public SysDept selectDeptById(Long id);

    /**
     * 添加部门
     */
    public boolean insertDept(SysDept dept);

    /**
     * 修改部门
     */
    public boolean updateDept(SysDept dept);
}
