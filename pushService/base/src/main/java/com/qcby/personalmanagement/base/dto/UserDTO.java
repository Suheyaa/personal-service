package com.qcby.personalmanagement.base.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author WYX
 * @version 1.0
 * @description: User
 * @date 2023/7/9 19:05
 */

@Data
public class UserDTO {
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
     * excel传过来的岗位id字符串
     */
    public String postName;

    /**
     * excel传过来的角色id字符串
     */
    public String roleName;


}
