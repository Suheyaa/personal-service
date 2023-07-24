package com.qcby.personalmanagement.base.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WYX
 * @version 1.0
 * @description: UserPostRole
 * @date 2023/7/10 0:50
 */

@Data
public class UserPostRoleDTO {
    /**
     * 用户id
     */
    public Long id;

    /**
     * 用户名称
     */
    public String userName;

    /**
     * 用户昵称
     */
    public String nickName;

    /**
     * 部门id
     */
    public Integer deptId;

    /**
     * 手机号
     */
    public String phone;

    /***
     * 密码
     */
    public String password;

    /**
     * 用户状态(0停用，1正常)
     */
    public Integer status;

    /**
     * 邮箱
     */
    public String email;

    /**
     * 性别
     */
    public Integer sex;

    /**
     * 备注
     */
    public String remark;

    /**
     * 创建时间
     */
    public LocalDateTime createTime;

    /**
     * 岗位id数组
     */
    public List<Long> postId;
    public List<String> postName;

    /**
     * 角色id数组
     */
    public List<Long> roleId;
    public List<String> roleName;


}
