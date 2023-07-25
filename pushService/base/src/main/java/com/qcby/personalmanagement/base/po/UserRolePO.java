package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WYX
 * @version 1.0
 * @description: UserANDRoles
 * @date 2023/7/10 0:13
 */
@Data
@TableName("sys_user_roles")
public class UserRolePO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long sysUserId;

    /**
     * 角色id
     */
    private Long sysRoleId;

}
