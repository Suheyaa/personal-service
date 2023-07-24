package com.qcby.personalmanagement.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcby.personalmanagement.base.po.DeptPO;
import com.qcby.personalmanagement.base.po.SysDeptPO;
import com.qcby.personalmanagement.base.po.UserPO;
import com.qcby.personalmanagement.base.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
    @Select("select  sys_dept.id,dept_name,user_name as leader from sys_dept " +
            "inner join sys_user on sys_dept.leader_id=sys_user.id where sys_dept.id= #{id}")
    UserVO selectDeptPro(Long id);  //传入dept的id

    @Select("select id,ancestors from sys_dept where ancestors like CONCAT('%',#{id},'%')")
    List<DeptPO> selectDeptByAncestor(String id);
}
