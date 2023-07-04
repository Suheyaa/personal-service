package com.qcby.personalmanagement.base.mapper;

import com.qcby.personalmanagement.base.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDeptMapper {


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
    public int deleteDeptById(Long id);

    /**
     * 检查部门是否存在用户
     * @param id
     * @return
     */
    public int checkDeptExistUser(Long id);

    /**
     * 检查部门是否存在下级部门
     * @param id
     * @return
     */
    public int hasChildByDeptId(Long id);

    /**
     * 根据id查询部门
     */
    public SysDept selectDeptById(Long id);

    /**
     * 添加部门
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部门
     */
    public int updateDept(SysDept dept);


}
